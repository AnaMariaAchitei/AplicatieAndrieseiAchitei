module AplicatieMuzica {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires AnimateFX;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
