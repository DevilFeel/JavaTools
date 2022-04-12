package io.github.devilfeel.clean.mvn;

import java.io.File;

/**
 * 清理Maven的仓库
 */
public class CleanMvn {
    /**
     * java -jar 执行任务
     *
     * @param args [0] maven仓库的路径
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            print("请输入maven仓库的路径信息");
            return;
        }
        findAndDelete(new File(args[0]));
        print("删除成功！！！");
    }

    public static boolean findAndDelete(File file) {
        if (!file.exists()) {
        } else if (file.isFile()) {
            if (file.getName().endsWith("lastUpdated")) {
                deleteFile(file.getParentFile());
                return true;
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (findAndDelete(f)) {
                    break;
                }
            }
        }
        return false;
    }

    public static void deleteFile(File file) {
        if (!file.exists()) {
        } else if (file.isFile()) {
            print("删除文件:" + file.getAbsolutePath());
            file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
            print("删除文件夹:" + file.getAbsolutePath());
            print("====================================");
            file.delete();
        }
    }

    public static void print(String msg) {
        System.out.println(msg);
    }
}
