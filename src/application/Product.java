package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;

public class Product {
	private LocalDate productDate;
	private SimpleStringProperty productPlace;
	private SimpleStringProperty productDescription;
	private SimpleStringProperty productTime;
	private SimpleStringProperty productSubject;
	
	public String getProductTime() {
		return productTime.get();
	}



	public void setProductTime(String productTime) {
		this.productTime = new SimpleStringProperty(productTime);
	}



	public String getProductSubject() {
		return productSubject.get();
	}



	public void setProductSubject(String productSubject) {
		this.productSubject = new SimpleStringProperty(productSubject);
	}



	public Product(String productSubject,LocalDate productDate,String productTime,String productPlace,String productDescription) {
		this.productDate=productDate;
		this.productPlace=new SimpleStringProperty(productPlace);
		this.productDescription=new SimpleStringProperty(productDescription);

		this.productTime=new SimpleStringProperty(productTime);

		this.productSubject=new SimpleStringProperty(productSubject);
	}



	public LocalDate getProductDate() {
		return productDate;
	}



	public void setProductDate(LocalDate productDate) {
		this.productDate = productDate;
	}



	public String getProductPlace() {
		return productPlace.get();
	}



	public void setProductPlace(String productPlace) {
		this.productPlace =new SimpleStringProperty( productPlace );
	}



	public String getProductDescription() {
		return productDescription.get();
	}



	public void setProductDescription(String productDescription) {
		this.productDescription = new SimpleStringProperty(productDescription);
	}
}
