package utils.diag;

// TEMP - Instrumentacion FASE 10 (medicion previa de esperas).
// INERTE salvo que se ejecute con -Dfase10.instrumentacion=true.
// Eliminar esta clase y todas las llamadas WaitProbe.* tras la corrida piloto.

import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Recolector de telemetria para las esperas del proyecto. No forma parte de la logica de prueba:
 * todas sus operaciones son no-op salvo que la propiedad de sistema {@code fase10.instrumentacion}
 * valga {@code true}. Escribe una linea JSON por espera en {@code target/fase10-esperas.jsonl}.
 */
public final class WaitProbe {

  private static final boolean ON =
      Boolean.parseBoolean(System.getProperty("fase10.instrumentacion", "false"));
  private static final Path OUT = Paths.get("target", "fase10-esperas.jsonl");
  private static final AtomicLong SEQ = new AtomicLong();
  private static final Object FILE_LOCK = new Object();
  private static final ThreadLocal<Deque<WaitProbe>> STACK =
      ThreadLocal.withInitial(ArrayDeque::new);

  private final long seq = SEQ.incrementAndGet();
  private final Long parentSeq;
  private final String clazz;
  private final String method;
  private final String condition;
  private final int nominalTimeoutSec;
  private final long tStart = System.nanoTime();
  private final Instant wallStart = Instant.now();

  private int iterations;
  private int searches;
  private long searchNanos;
  private long sleepNanos;
  private long openSearchMark;
  private long openSleepMark;
  private int lastElements = -1;
  private Long nanosUntilFound;
  private String outcome;
  private String matched;

  private WaitProbe(
      String clazz, String method, int nominalTimeoutSec, String condition, Long parentSeq) {
    this.clazz = clazz;
    this.method = method;
    this.nominalTimeoutSec = nominalTimeoutSec;
    this.condition = condition;
    this.parentSeq = parentSeq;
  }

  public static void begin(String clazz, String method, int nominalTimeoutSec, Object condition) {
    if (!ON) {
      return;
    }
    Deque<WaitProbe> stack = STACK.get();
    Long parent = stack.isEmpty() ? null : stack.peek().seq;
    stack.push(
        new WaitProbe(clazz, method, nominalTimeoutSec, String.valueOf(condition), parent));
  }

  public static void iter() {
    WaitProbe p = top();
    if (p != null) {
      p.iterations++;
    }
  }

  public static void searchStart() {
    WaitProbe p = top();
    if (p != null) {
      p.searches++;
      p.openSearchMark = System.nanoTime();
    }
  }

  public static void searchEnd(int elements) {
    WaitProbe p = top();
    if (p != null && p.openSearchMark != 0L) {
      p.searchNanos += System.nanoTime() - p.openSearchMark;
      p.openSearchMark = 0L;
      if (elements >= 0) {
        p.lastElements = elements;
      }
    }
  }

  public static void sleepStart() {
    WaitProbe p = top();
    if (p != null) {
      p.openSleepMark = System.nanoTime();
    }
  }

  public static void sleepEnd() {
    WaitProbe p = top();
    if (p != null && p.openSleepMark != 0L) {
      p.sleepNanos += System.nanoTime() - p.openSleepMark;
      p.openSleepMark = 0L;
    }
  }

  public static void foundNow(String matched) {
    WaitProbe p = top();
    if (p != null && p.nanosUntilFound == null) {
      p.nanosUntilFound = System.nanoTime() - p.tStart;
      p.matched = matched;
    }
  }

  public static void outcome(String outcome) {
    WaitProbe p = top();
    if (p != null && p.outcome == null) {
      p.outcome = outcome;
    }
  }

  public static void end() {
    if (!ON) {
      return;
    }
    Deque<WaitProbe> stack = STACK.get();
    if (stack.isEmpty()) {
      return;
    }
    WaitProbe p = stack.pop();
    try {
      if (p.openSearchMark != 0L) {
        p.searchNanos += System.nanoTime() - p.openSearchMark;
        p.openSearchMark = 0L;
      }
      if (p.openSleepMark != 0L) {
        p.sleepNanos += System.nanoTime() - p.openSleepMark;
        p.openSleepMark = 0L;
      }
      p.write();
    } catch (Throwable ignored) {
      // La medicion nunca afecta a la prueba.
    }
  }

  private static WaitProbe top() {
    if (!ON) {
      return null;
    }
    Deque<WaitProbe> stack = STACK.get();
    return stack.isEmpty() ? null : stack.peek();
  }

  private void write() throws Exception {
    long total = System.nanoTime() - tStart;
    String finalOutcome =
        outcome != null ? outcome : (nanosUntilFound != null ? "FOUND" : "EXCEPTION");

    String caller = "?";
    for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
      String c = e.getClassName();
      if (c.startsWith("tasks.") || c.startsWith("stepDefinitions.")) {
        caller = c + "." + e.getMethodName();
        break;
      }
    }

    String line =
        "{"
            + "\"seq\":"
            + seq
            + ",\"parent\":"
            + parentSeq
            + ",\"thread\":\""
            + esc(Thread.currentThread().getName())
            + "\""
            + ",\"wallStart\":\""
            + wallStart
            + "\""
            + ",\"wallEnd\":\""
            + Instant.now()
            + "\""
            + ",\"class\":\""
            + esc(clazz)
            + "\""
            + ",\"method\":\""
            + esc(method)
            + "\""
            + ",\"caller\":\""
            + esc(caller)
            + "\""
            + ",\"nominalTimeoutSec\":"
            + nominalTimeoutSec
            + ",\"durationMs\":"
            + ms(total)
            + ",\"iterations\":"
            + iterations
            + ",\"searches\":"
            + searches
            + ",\"searchesPerIter\":"
            + (iterations > 0 ? round2((double) searches / iterations) : "0")
            + ",\"searchMs\":"
            + ms(searchNanos)
            + ",\"sleepMs\":"
            + ms(sleepNanos)
            + ",\"overheadMs\":"
            + ms(total - searchNanos - sleepNanos)
            + ",\"elementsFound\":"
            + lastElements
            + ",\"msUntilFound\":"
            + (nanosUntilFound != null ? String.valueOf(ms(nanosUntilFound)) : "null")
            + ",\"outcome\":\""
            + esc(finalOutcome)
            + "\""
            + ",\"matched\":"
            + (matched != null ? "\"" + esc(matched) + "\"" : "null")
            + ",\"condition\":\""
            + esc(condition)
            + "\""
            + "}";

    synchronized (FILE_LOCK) {
      Path parent = OUT.getParent();
      if (parent != null) {
        Files.createDirectories(parent);
      }
      try (Writer w =
          Files.newBufferedWriter(
              OUT,
              StandardCharsets.UTF_8,
              StandardOpenOption.CREATE,
              StandardOpenOption.APPEND)) {
        w.write(line);
        w.write(System.lineSeparator());
      }
    }
  }

  private static long ms(long nanos) {
    return nanos / 1_000_000L;
  }

  private static String round2(double d) {
    return String.format(Locale.ROOT, "%.2f", d);
  }

  private static String esc(String s) {
    if (s == null) {
      return "";
    }
    return s.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", " ")
        .replace("\r", " ");
  }
}
