package br.com.gft.testautomation.common.model;

/*Model class Parameter that correspond to the Parameter entity in the database
 *It's used to personalise the application change names of labels or showing or not buttons.  */
public class Parameter {

	private Integer id_parameter;
	private String project_name;
	private Integer importJIRAxmlButton;
	
	public long getId_parameter() {
		return id_parameter;
	}
	public void setId_parameter(Integer id_parameter) {
		this.id_parameter = id_parameter;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public Integer getImportJIRAxmlButton() {
		return importJIRAxmlButton;
	}
	public void setImportJIRAxmlButton(Integer importJIRAxmlButton) {
		this.importJIRAxmlButton = importJIRAxmlButton;
	}
	

}
