package com.bc.finance.common.helper;

import com.bc.finance.common.utils.FileUtils;
import com.bc.finance.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Excel帮助类
 */
public class ExcelHelper {
    static ExcelHelper excelHelper = new ExcelHelper();

    //获取用户上传的exxcel数据，转化成map
    public static List<Map<String, String>> getFileData(MultipartFile file) {

        List<String> dict = new ArrayList<>(); //excel第一行作为数据字典
        List<Map<String, String>> data = new ArrayList<>();
        InputStream ins=null;
        try {
            ins = file.getInputStream();
            Workbook book;
            if(file.getOriginalFilename().indexOf("xlsx")>-1) {
                book = new XSSFWorkbook(ins);
            } else {
                book = new HSSFWorkbook(ins);
            }

            Sheet sheet = book.getSheetAt(0);
            Row firstRow = sheet.getRow(0);
            int cellNum = firstRow.getPhysicalNumberOfCells(); //多少列
            for (int i = 0; i < cellNum; i++) { //读取表头字段
                Cell cell = firstRow.getCell(i);
                if (cell != null) {
                    String val = cell.getStringCellValue().trim();
                    if (!Objects.equals(val, ""))
                        dict.add(val);
                }
            }

            int rowNum = sheet.getPhysicalNumberOfRows(); //多少行
            //跳过表头进行遍历
            for (int i = 1; i < rowNum; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    HashMap<String, String> map = new HashMap<>();
                    for (int j = 0; j < dict.size(); j++) {
                        String key = dict.get(j);
                        String val = "";
                        Cell cell = row.getCell(j);
                        if(j==0 ) {
                            // 第一列必须有值
                            if(cell==null) break;
                            cell.setCellType(CellType.STRING);
                            if(StringUtils.isBlank(cell.getStringCellValue())) break;
                        }
                        if (cell != null) {
                            cell.setCellType(CellType.STRING);
                            val = cell.getStringCellValue().trim();
                        }
                        map.put(key, val);
                    }
                    if (map.size() != 0)
                        data.add(map);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert ins != null;
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static List<Map<String, String>> getFileData(List<String> dict, InputStream ins) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            Workbook book;
            try{
                book = new HSSFWorkbook(ins);
            } catch(Exception e) {
                book = new XSSFWorkbook(ins);
            }
            Sheet sheet = book.getSheetAt(0);
            int rowNum = sheet.getPhysicalNumberOfRows(); //多少行

            Row firstRow = sheet.getRow(0);
            int cellNum = firstRow.getPhysicalNumberOfCells(); //多少列

            //跳过表头进行遍历
            for (int i = 1; i < rowNum; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    HashMap<String, String> map = new HashMap<>();
                    for (int j = 0; j < cellNum; j++) {
                        String key = dict.get(j);
                        String val = "";
                        Cell cell = row.getCell(j);
                        if(j==0 ) {
                            cell.setCellType(CellType.STRING);
                            if(StringUtils.isBlank(cell.getStringCellValue()))
                            break;
                        }
                        if (cell != null) {
                            cell.setCellType(CellType.STRING);
                            val = cell.getStringCellValue().trim();
                        }
                        map.put(key, val);
                    }
                    if (map.size() != 0)
                        data.add(map);

                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                assert ins != null;
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /** 把数据导出为excel, 列数获取数据第一行
     * @param data
     * @param req
     * @param resp
     */
    public static void exportFileData(List<List> data, String fileName, HttpServletRequest req, HttpServletResponse resp) {
        ExcelHelper.exportFileData(data, fileName, null, null, req, resp);
    }

    /** 把数据导出为excel, 列数获取数据第一行
     * @param data
     * @param rols  需要合并的列
     * @param cols  需要合并的行
     * @param req
     * @param resp
     */
    public static void exportFileData(List<List> data, String fileName, String rols, String cols, HttpServletRequest req, HttpServletResponse resp) {
        try {
            HSSFWorkbook book = new HSSFWorkbook();
            Sheet sheet = book.createSheet();
            int rowNum = 0;
            for (List rowData : data) {
                Row row = sheet.createRow(rowNum);
                int colNum = 0;
                for (Object colData : rowData) {
                    row.createCell(colNum).setCellValue(String.valueOf(colData));
                    colNum++;
                }
                rowNum++;
            }

            excelHelper.merge(sheet, rols, cols);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            book.write(out);
            book.close();

            FileUtils.exportByteFile(out.toByteArray(), Optional.ofNullable(fileName).orElse("office.xls"), req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportFileData(List<List> data, String fileName, List<CellRangeAddress> regions, HttpServletRequest req, HttpServletResponse resp) {
        InputStream in = null;
        try {
            HSSFWorkbook book = new HSSFWorkbook();
            Sheet sheet = book.createSheet();
            int rowNum = 0;
            for (List rowData : data) {
                Row row = sheet.createRow(rowNum);
                int colNum = 0;
                for (Object colData : rowData) {
                    row.createCell(colNum).setCellValue(String.valueOf(colData));
                    colNum++;
                }
                rowNum++;
            }

            regions.forEach(e->sheet.addMergedRegionUnsafe(e));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            book.write(out);
            book.close();

            FileUtils.exportByteFile(out.toByteArray(), Optional.ofNullable(fileName).orElse("office.xls"), req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void merge(Sheet sheet, String rols, String cols) {
        int totalRols = sheet.getPhysicalNumberOfRows();
        int totalCellNum = sheet.getRow(0).getPhysicalNumberOfCells();
        if(StringUtils.isNotBlank(rols)) {
            for (String rol : rols.split(",")) {
                int startRowNum = 0;
                int endRowNum = 1;
                int rownum = Integer.parseInt(rol);

                while (endRowNum <= totalRols) {
                    Row startRow = sheet.getRow(startRowNum);
                    Row endstartRow = sheet.getRow(endRowNum);
                    if (endRowNum < totalRols && startRow.getCell(rownum).getStringCellValue().equals(endstartRow.getCell(rownum).getStringCellValue())) {
                        endRowNum++;
                    } else {
                        if (endRowNum - startRowNum != 1) {
                            CellRangeAddress region = new CellRangeAddress(startRowNum, endRowNum - 1, rownum, rownum);
                            if (validateMergedRegions(sheet, region)) sheet.addMergedRegion(region);
                        }
                        startRowNum = endRowNum;
                        endRowNum = startRowNum + 1;
                    }
                }
            }
        }

        if(StringUtils.isNotBlank(cols)) {
            for (String col : cols.split(",")) {
                Row row = sheet.getRow(Integer.parseInt(col));
                int startCellNum = 0;
                int endCellNum = 1;
                int cellNum = Integer.parseInt(col);

                while (endCellNum <= totalCellNum) {
                    Cell startCell = row.getCell(startCellNum);
                    Cell endstartCell = row.getCell(endCellNum);
                    if (endCellNum < totalCellNum && startCell.getStringCellValue().equals(endstartCell.getStringCellValue())) {
                        endCellNum++;
                    } else {
                        if (endCellNum - startCellNum != 1) {
                            CellRangeAddress region = new CellRangeAddress(cellNum, cellNum, startCellNum, endCellNum - 1);
                            if (validateMergedRegions(sheet, region)) sheet.addMergedRegion(region);
                        }
                        startCellNum = endCellNum;
                        endCellNum = startCellNum + 1;
                    }
                }
            }
        }

        for (int i = 0; i < totalCellNum; i++) {
            int startRowNum = 0;
            int endRowNum = 1;
            int rownum = i;

            while(endRowNum <= cols.length()) {
                Row startRow = sheet.getRow(startRowNum);
                Row endstartRow = sheet.getRow(endRowNum);
                if(endRowNum < cols.length() && startRow.getCell(rownum).getStringCellValue().equals(endstartRow.getCell(rownum).getStringCellValue())) {
                    endRowNum++;
                } else {
                    if(endRowNum - startRowNum != 1) {
                        CellRangeAddress region = new CellRangeAddress(startRowNum, endRowNum-1, rownum, rownum);
                        if(validateMergedRegions(sheet, region)) sheet.addMergedRegion(region);
                    }
                    startRowNum = endRowNum;
                    endRowNum = startRowNum + 1;
                }
            }
        }
    }

    private boolean validateMergedRegions(Sheet sheet, CellRangeAddress region) {
        for (CellRangeAddress mergedRegion : sheet.getMergedRegions()) {
            if (mergedRegion.intersects(region)) return false;
        }

        return true;
    }
}
