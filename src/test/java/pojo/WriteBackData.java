package pojo;

public class WriteBackData {
	private int rowNum;
	private int cellNum;
	private String content;

	public WriteBackData() {
		super();
	}

	public WriteBackData(int rowNum, int cellNum, String content) {
		super();
		this.rowNum = rowNum;
		this.cellNum = cellNum;
		this.content = content;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getCellNum() {
		return cellNum;
	}

	public void setCellNum(int cellNum) {
		this.cellNum = cellNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "WriterBackData [rowNum=" + rowNum + ", cellNum=" + cellNum + ", content=" + content + "]";
	}

}
