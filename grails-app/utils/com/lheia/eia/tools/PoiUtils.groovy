package com.lheia.eia.tools

/**
 * Created by Tianlei on 2016/12/6.
 */

import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class PoiUtils {


    public static void exportExcel(String dirName, String filename ,List titleList,List datas){
        try {
            Workbook wb = new XSSFWorkbook();
            XSSFSheet sheet;
            if(wb.getSheet("Sheet1")==null){
                sheet = wb.createSheet("Sheet1");
            }else{
                sheet=wb.getSheet("Sheet1");
            }
            XSSFRow row = sheet.createRow(0);
            for(int k=0;k<titleList.size();k++){
                XSSFCell cell = row.createCell(k);
                cell.setCellValue(titleList.get(k));
            }
            if(datas){
                for(int i=0;i<datas.size();i++){
                    XSSFRow rowData = sheet.createRow(1+i);
                    def data = datas.get(i)
                    for(int j=0;j<titleList.size();j++){
                        XSSFCell cell = rowData.createCell(j);
                        cell.setCellValue(data[j]);
                    }
                }
            }
            File file =new File(dirName);
            if(!file.exists()&&!file.isDirectory()) {
                file.mkdirs();
            }
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(dirName+filename);
            wb.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static Workbook exportExcel(List titleList,List datas){
        try {
            Workbook wb = new XSSFWorkbook();
            XSSFSheet sheet;
            if(wb.getSheet("Sheet1")==null){
                sheet = wb.createSheet("Sheet1");
            }else{
                sheet=wb.getSheet("Sheet1");
            }
            XSSFRow row = sheet.createRow(0);
            for(int k=0;k<titleList.size();k++){
                XSSFCell cell = row.createCell(k);
                cell.setCellValue(titleList.get(k));
            }
            if(datas){
                for(int i=0;i<datas.size();i++){
                    XSSFRow rowData = sheet.createRow(1+i);
                    def data = datas.get(i)
                    for(int j=0;j<titleList.size();j++){
                        XSSFCell cell = rowData.createCell(j);
                        cell.setCellValue(data[j]);
                    }
                }
            }
            return wb
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
