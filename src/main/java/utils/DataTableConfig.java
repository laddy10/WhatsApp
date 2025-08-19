package utils;

import models.User;

import java.util.Map;

public class DataTableConfig {

 //   @DataTableType
    public User userEntry(Map<String, String> entry) {
        User user = new User();

        user.setNumero(entry.getOrDefault("numero", ""));
        user.setSaludo(entry.getOrDefault("saludo", ""));
        user.setValor(entry.getOrDefault("valor", ""));


        return user;
    }
}