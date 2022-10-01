package com.gustavo.excel;

import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WriteExcelMultiThread {
    public static final int sheetNum = 100;
    public static void main(String[] args) {
        multihread();
    }

    public static void multihread(){
        long start = System.currentTimeMillis();
        System.out.println("begin multi thread.");
        String filePath = "E:\\22.xlsx";

        SXSSFWorkbook sxssfWorkbook = null;
        BufferedOutputStream outputStream = null;
        try {
            //这样表示SXSSFWorkbook只会保留100条数据在内存中，其它的数据都会写到磁盘里，这样的话占用的内存就会很少
            sxssfWorkbook = new SXSSFWorkbook(getXSSFWorkbook(filePath),100);
            ExecutorService executorService = Executors.newCachedThreadPool();
            CountDownLatch countDownLatch = new CountDownLatch(sheetNum);
            //获取第一个Sheet页
            for (int sheetNum = 0; sheetNum < WriteExcelMultiThread.sheetNum; sheetNum++) {
                SXSSFSheet sheet = sxssfWorkbook.createSheet("sheet_" + (sheetNum + 1));
                executorService.submit(new oneSheetThread(sheet, countDownLatch));
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            sxssfWorkbook.write(outputStream);
            outputStream.flush();
            sxssfWorkbook.dispose();// 释放workbook所占用的所有windows资源
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("one thread exec over");
        System.out.println("used time:  " + (System.currentTimeMillis() - start));
    }


    public static void oneThread(){
        System.out.println("begin one thread.");
        long start = System.currentTimeMillis();
        String filePath = "E:\\11.xlsx";

        SXSSFWorkbook sxssfWorkbook = null;
        BufferedOutputStream outputStream = null;
        try {
            //这样表示SXSSFWorkbook只会保留100条数据在内存中，其它的数据都会写到磁盘里，这样的话占用的内存就会很少
            sxssfWorkbook = new SXSSFWorkbook(getXSSFWorkbook(filePath),100);
            //获取第一个Sheet页
            for (int sheetNum = 0; sheetNum < WriteExcelMultiThread.sheetNum; sheetNum++) {
                SXSSFSheet sheet = sxssfWorkbook.createSheet("sheet_" + (sheetNum + 1));
                for (int rowNum = 0; rowNum < 10000; rowNum++) {
                    SXSSFRow therow = sheet.createRow(rowNum);
                    for (int columnNum = 0; columnNum < 10; columnNum++) {
                        therow.createCell(columnNum).setCellValue(String.format("sheet_%s,row_%s,column=%s", sheetNum, rowNum, columnNum));
                    }
                }

            }

            outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            sxssfWorkbook.write(outputStream);
            outputStream.flush();
            sxssfWorkbook.dispose();// 释放workbook所占用的所有windows资源
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("one thread exec over");
        System.out.println("used time:  " + (System.currentTimeMillis() - start));
    }

    /**
     * 先创建一个XSSFWorkbook对象
     * @param filePath
     * @return
     */
    public static XSSFWorkbook getXSSFWorkbook(String filePath) {
        XSSFWorkbook workbook =  null;
        BufferedOutputStream outputStream = null;
        try {
            File fileXlsxPath = new File(filePath);
            outputStream = new BufferedOutputStream(new FileOutputStream(fileXlsxPath));
            workbook = new XSSFWorkbook();
            workbook.createSheet("测试Sheet");
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }


    public static class oneSheetThread implements Runnable{
        private SXSSFSheet sheet;
        private CountDownLatch countDownLatch;

        public oneSheetThread(SXSSFSheet sheet, CountDownLatch countDownLatch) {
            this.sheet = sheet;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("begin to exec: " + sheet.getSheetName());
            try {
                for (int rowNum = 0; rowNum < 10000; rowNum++) {
                    SXSSFRow therow = sheet.createRow(rowNum);
                    for (int columnNum = 0; columnNum < 10; columnNum++) {
                        therow.createCell(columnNum).setCellValue(String.format("%s,row_%s,column=%s", sheet.getSheetName(), rowNum, columnNum));
                    }
                }
                System.out.println("exec over, :" +sheet.getSheetName());
            }finally {
                countDownLatch.countDown();
            }
        }
    }
}
