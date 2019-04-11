package com.wsf.util;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static void getQueryId(String[] paths){
        if(paths==null||paths.length==0) return;

        File[] filePaths=new File[paths.length];
        Pattern pattern=Pattern.compile("queryId:\"[0-9a-zA-Z]{32}\"");
        Matcher matcher=null;
        StringBuilder show=new StringBuilder();

        for(int i=0;i<filePaths.length;i++){
            filePaths[i]=new File(paths[i]);
        }
        StringBuilder sb=new StringBuilder();
        for(File f:filePaths){
            if(!f.exists()) continue;
            try(FileInputStream fis=new FileInputStream(f);
                InputStreamReader isr=new InputStreamReader(fis,"utf-8");
                BufferedReader br=new BufferedReader(isr);){
                String s="";

                while ((s=br.readLine())!=null){
                    sb.append(s);
                }

                matcher=pattern.matcher(sb.toString());
                show.append(f.getAbsolutePath()+":\n");
                while(matcher.find()){
                    show.append(matcher.group()+"\n");
                }
                show.append("\n");


            }catch (IOException e) {
                e.printStackTrace();
            }
        }


        System.out.println(show.toString());



    }

    public static String[] getJsFiles(String folderPath){
        Set<String> paths=new LinkedHashSet<>();
        doGetJsFiles(folderPath,paths);
        return paths.toArray(new String[paths.size()]);
    }
    private static void doGetJsFiles(String folderPath,Set<String> paths){
        if(folderPath==null) return;

        File folder=new File(folderPath);

        if (!folder.exists()) return;


        File[] files=folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {

                if(pathname.getName().endsWith(".js")) return true;

                if(pathname.isDirectory()) return true;

                return false;

            }
        });

        for(File f:files){


            if(f.isFile()) {

                paths.add(f.getAbsolutePath());

            }else{
                doGetJsFiles(f.getAbsolutePath(),paths);
            }
        }

    }
    public static void main(String[] args) {





        getQueryId(getJsFiles("C:\\Users\\SrV\\Downloads"));
    }

}
