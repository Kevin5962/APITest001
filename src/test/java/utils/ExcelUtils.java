package utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import constant.Constants;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import pojo.WriteBackData;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
	public static List<WriteBackData> wbdList = new ArrayList<WriteBackData>();

	// 获取指定sheet的内容
	// T代表传入的类型，T从哪里传入，从参数
	// 首先在public static与返回值之间的<T>必不可少，这表明这是一个泛型方法，并且声明了一个泛型List<T>
	// 与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
	public static <E> List<E> read(Class<E> clazz, int sheetIndex) {
		FileInputStream fis = null;
		List<E> importExcel = null;
		try {
			fis = new FileInputStream(Constants.EXCEL_PATH);
			ImportParams params = new ImportParams();
			// 读取第几个sheet
			params.setStartSheetIndex(sheetIndex);
			importExcel = ExcelImportUtil.importExcel(fis, clazz, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fis);
		}
		return importExcel;
	}

	public static void backWrite(String sheetName) {
		// 1、excel加载到内存中
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(Constants.EXCEL_PATH);
			Workbook workbook = WorkbookFactory.create(fis);
			// 2、获取指定Sheet
			Sheet sheet = workbook.getSheet(sheetName);
			// 3、循环回写集合wbdList
			for (WriteBackData wbd : wbdList) {
				int rowNum = wbd.getRowNum();
				int cellNum = wbd.getCellNum();
				String content = wbd.getContent();
				Row row = sheet.getRow(rowNum);
				Cell cell = row.getCell(cellNum, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(content);
			}
			// 4、回写
			fos = new FileOutputStream(Constants.EXCEL_PATH);
			workbook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fis);
			close(fos);
		}
	}
	
	public static void close(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
