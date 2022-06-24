module edu.br.femass.lojabrinquedo {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.postgresql.jdbc;
    requires java.sql;

    opens edu.br.femass.lojabrinquedo to javafx.fxml;
    exports edu.br.femass.lojabrinquedo;
    exports edu.br.femass.lojabrinquedo.Controller;
    opens edu.br.femass.lojabrinquedo.Controller to javafx.fxml;
}