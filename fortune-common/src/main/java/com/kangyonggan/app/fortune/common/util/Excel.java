package com.kangyonggan.app.fortune.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * created by brave
 * Excel操作相关
 */
public class Excel {

    /**
     * 得到文件的扩展名
     *
     * @param fileName 文件全名
     * @return 文件扩展名
     */
    private static String getFileExtension(String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            fileName = fileName.trim();
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName 文件全名
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> excelToList(String fileName) throws Exception {
        return excelToList(fileName, 1);
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName  文件全名
     * @param startLine 起始行,从1开始
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> excelToList(String fileName, int startLine) throws Exception {
        String extension = getFileExtension(fileName);
        if (StringUtils.isNotEmpty(extension)) {
            if (extension.trim().equals("xlsx")) {
                return excelToList(fileName, "2007", startLine, true);
            } else if (extension.trim().equals("xls")) {
                return excelToList(fileName, "2003", startLine, true);
            } else {
                throw new Exception("文件类型不对, 必须为excel类型！");
            }
        }

        return null;
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName     文件全名
     * @param startLine    起始行,从1开始
     * @param isSheetFirst 是否只解析第一个sheet
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> excelToList(String fileName, int startLine, boolean isSheetFirst) throws Exception {
        String extension = getFileExtension(fileName);
        if (StringUtils.isNotEmpty(extension)) {
            if (extension.trim().equals("xlsx")) {
                return excelToList(fileName, "2007", startLine, isSheetFirst);
            } else if (extension.trim().equals("xls")) {
                return excelToList(fileName, "2003", startLine, isSheetFirst);
            } else {
                throw new Exception("文件类型不对, 必须为excel类型！");
            }
        }

        return null;
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName  文件全名
     * @param startLine 起始行,从1开始
     * @param sheetNum  解析第几个sheet,从1开始
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> excelToList(String fileName, int startLine, int sheetNum) throws Exception {
        String extension = getFileExtension(fileName);
        if (StringUtils.isNotEmpty(extension)) {
            if (extension.trim().equals("xlsx")) {
                return excelToList(fileName, "2007", startLine, sheetNum);
            } else if (extension.trim().equals("xls")) {
                return excelToList(fileName, "2003", startLine, sheetNum);
            } else {
                throw new Exception("文件类型不对, 必须为excel类型！");
            }
        }

        return null;
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName     文件全名
     * @param fileType     文件类型，2003或2007
     * @param startLine    起始行,从1开始
     * @param isSheetFirst 是否只解析第一个sheet
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> excelToList(String fileName, String fileType, int startLine, boolean isSheetFirst) throws Exception {
        if (exists(fileName) && StringUtils.isNotEmpty(fileType)) {
            List<String[]> list = new ArrayList();

            FileInputStream fis = new FileInputStream(fileName);
            Workbook wb;
            if (fileType.trim().equals("2003")) {
                wb = new HSSFWorkbook(fis);
            } else if (fileType.trim().equals("2007")) {
                wb = new XSSFWorkbook(fis);
            } else {
                throw new FileNotFoundException("文件类型必须为'2003'或'2007'！");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (wb.getNumberOfSheets() > 1 && !isSheetFirst) {
                // 不止一个sheet
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    Sheet sheet = wb.getSheetAt(i);
                    loopExcelSheet(list, sheet, startLine, sdf);
                }
            } else {
                Sheet sheet = wb.getSheetAt(0);
                loopExcelSheet(list, sheet, startLine, sdf);
            }
            return list;


        } else {
            throw new FileNotFoundException("文件不存在！");
        }

    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName  文件全名
     * @param fileType  文件类型，2003或2007
     * @param startLine 起始行,从1开始
     * @param sheetNum  解析第几个sheet,从1开始
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> excelToList(String fileName, String fileType, int startLine, int sheetNum) throws Exception {
        if (exists(fileName) && StringUtils.isNotEmpty(fileType)) {
            List<String[]> list = new ArrayList();

            FileInputStream fis = new FileInputStream(fileName);
            Workbook wb;
            if (fileType.trim().equals("2003")) {
                wb = new HSSFWorkbook(fis);
            } else if (fileType.trim().equals("2007")) {
                wb = new XSSFWorkbook(fis);
            } else {
                throw new FileNotFoundException("文件类型必须为'2003'或'2007'！");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Sheet sheet = wb.getSheetAt(sheetNum - 1);
            loopExcelSheet(list, sheet, startLine, sdf);
            return list;
        } else {
            throw new FileNotFoundException("文件不存在！");
        }

    }

    /**
     * 循环sheet
     *
     * @param list
     * @param sheet
     * @param startLine
     * @param sdf
     */
    private static void loopExcelSheet(List<String[]> list, Sheet sheet, int startLine, SimpleDateFormat sdf) {
        int line = 1; //当前行

        for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext(); ) {
            Row r = rowIt.next();
            if (line < startLine) {
                line++;
                continue;
            }
            int n = 0;
            String[] strArray = new String[r.getLastCellNum()];
//            for (Iterator<Cell> cellIt = r.iterator(); cellIt.hasNext(); ) {
            for (int i = 0; i < r.getLastCellNum(); i++) {
                Cell cell = r.getCell(i);
                String cellContent = "";
                if (cell != null) {
                    switch (cell.getCellType()) {
                        // 数字
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            //如果是日期
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                cellContent = sdf.format(cell.getDateCellValue());
                            } else {
                                Double value = cell.getNumericCellValue();
                                DecimalFormat df = new DecimalFormat("#.###");
                                cellContent = df.format(value);
                            }
                            break;
                        // 字符串
                        case HSSFCell.CELL_TYPE_STRING:
                            cellContent = cell.getStringCellValue();
                            break;
                        // boolean
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            cellContent = cell.getBooleanCellValue() + "";
                            break;
                        // 公式
                        case HSSFCell.CELL_TYPE_FORMULA:
                            cellContent = cell.getCellFormula();
                            break;
                        // 空值
                        case HSSFCell.CELL_TYPE_BLANK:
                            break;
                        // 错误
                        case HSSFCell.CELL_TYPE_ERROR:
                            break;
                        default:
                            break;
                    }
                }
                strArray[n] = cellContent.trim();
                n++;
            }
            if (!StringUtil.isEmpty(strArray)) {
                list.add(strArray);
            }
            line++;
        }

    }

    /**
     * 根据文件名判断文件是否存在
     *
     * @param fileName 文件全名
     * @return
     */
    public static boolean exists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 写数据到Excel
     *
     * @param path
     * @param dataList
     * @throws Exception
     */
    public static void exportExcel(String path, List<String[]> dataList) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        for (int i = 0; i < dataList.size(); i++) {
            String[] data = dataList.get(i);
            HSSFRow row = sheet.createRow(i);
            for (int j = 0; j < data.length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(data[j]);
            }
        }

        OutputStream out = null;
        try {
            out = new FileOutputStream(path);
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw e;
                }

            }
        }
    }

}
