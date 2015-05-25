package br.com.gft.testautomation.common.export;

/** Class that stores the necessary data to populate the MS-Excel file header */
public class PopulateHeader {

	/* Fields used in the MS-Excel file */
	private long excelIdTicket;
	private String excelJira;
	private String excelRelease;
	private String excelDescription;
	private String excelEnvironment;
	private String excelDeveloper;
	private String excelTester;
	private String excelTime;
	private boolean fileBlank;

	//Setters and Getters
	public String getExcelJira() {
		return excelJira;
	}
	public void setExcelJira(String excelJira) {
		this.excelJira = excelJira;
	}
	public String getExcelRelease() {
		return excelRelease;
	}
	public void setExcelRelease(String excelRelease) {
		this.excelRelease = excelRelease;
	}
	public String getExcelDescription() {
		return excelDescription;
	}
	public void setExcelDescription(String excelDescription) {
		this.excelDescription = excelDescription;
	}
	public String getExcelEnvironment() {
		return excelEnvironment;
	}
	public void setExcelEnvironment(String excelEnvironment) {
		this.excelEnvironment = excelEnvironment;
	}
	public String getExcelDeveloper() {
		return excelDeveloper;
	}
	public void setExcelDeveloper(String excelDeveloper) {
		this.excelDeveloper = excelDeveloper;
	}
	public String getExcelTester() {
		return excelTester;
	}
	public void setExcelTester(String excelTester) {
		this.excelTester = excelTester;
	}
	public String getExcelTime() {
		return excelTime;
	}
	public void setExcelTime(String excelTime) {
		this.excelTime = excelTime;
	}
	public long getExcelIdTicket() {
		return excelIdTicket;
	}
	public void setExcelIdTicket(long excelIdTicket) {
		this.excelIdTicket = excelIdTicket;
	}
	public boolean isFileBlank() {
		return fileBlank;
	}
	public void setFileBlank(boolean fileBlank) {
		this.fileBlank = fileBlank;
	}
}
