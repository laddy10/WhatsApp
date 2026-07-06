package hooks;

import cucumber.api.java.Before;
import net.thucydides.core.steps.StepEventBus;

public class RegisterMetricasListener {

  private static final MetricasStepListener LISTENER = new MetricasStepListener();

  @Before(order = 1)
  public void registerListener() {
    if (StepEventBus.getEventBus() != null) {
      StepEventBus.getEventBus().dropListener(LISTENER);
      StepEventBus.getEventBus().registerListener(LISTENER);
    }
  }
}