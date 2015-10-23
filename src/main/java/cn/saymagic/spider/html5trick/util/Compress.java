package cn.saymagic.spider.html5trick.util;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.extract.ExtractArchive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.commons.logging.impl.LogKitLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by saymagic on 15/10/21.
 */
public class Compress {

    public static void unrar(String srcRarPath, String dstDirectoryPat) {
        final File rar = new File(srcRarPath);
        final File destinationFolder = new File(dstDirectoryPat);
        extractArchive(rar, destinationFolder);
    }

    public static void extractArchive(File archive, File destination) {
        Archive arch = null;

        try {
            arch = new Archive(archive);
        } catch (RarException var10) {
            var10.printStackTrace();
        } catch (IOException var11) {
            var11.printStackTrace();
        }

        if (arch != null) {
            if (arch.isEncrypted()) {
                System.out.println("archive is encrypted cannot extreact");
                return;
            }

            FileHeader fh = null;

            while (true) {
                fh = arch.nextFileHeader();
                if (fh == null) {
                    break;
                }

                String fileNameString = fh.getFileNameString();
                if (fh.isEncrypted()) {
                    System.out.println("file is encrypted cannot extract: " + fileNameString);
                } else {
                    System.out.println("extracting: " + fileNameString);
                    try {
                        if (fh.isDirectory()) {
                            createDirectory(fh, destination);
                        } else {
                            File e = createFile(fh, destination);
                            FileOutputStream stream = new FileOutputStream(e);
                            arch.extractFile(fh, stream);
                            stream.close();
                        }
                    } catch (IOException var8) {
                        System.out.println(var8.toString() + "error extracting the file");
                    } catch (RarException var9) {
                        System.out.println(var9.toString() + "error extraction the file");
                    }
                }
            }
        }

    }

    private static File makeFile(File destination, String name) throws IOException {
        String[] dirs = name.split("\\\\");
        if (dirs == null) {
            return null;
        } else {
            String path = "";
            int size = dirs.length;
            if (size == 1) {
                return new File(destination, name);
            } else if (size <= 1) {
                return null;
            } else {
                for (int f = 0; f < dirs.length - 1; ++f) {
                    path = path + File.separator + dirs[f];
                    (new File(destination, path)).mkdir();
                }

                path = path + File.separator + dirs[dirs.length - 1];
                File var6 = new File(destination, path);
                var6.createNewFile();
                return var6;
            }
        }
    }

    private static void createDirectory(FileHeader fh, File destination) {
        File f = null;
        if (fh.isDirectory() && fh.isUnicode()) {
            f = new File(destination, fh.getFileNameW());
            if (!f.exists()) {
                makeDirectory(destination, fh.getFileNameW());
            }
        } else if (fh.isDirectory() && !fh.isUnicode()) {
            f = new File(destination, fh.getFileNameString());
            if (!f.exists()) {
                makeDirectory(destination, fh.getFileNameString());
            }
        }

    }

    private static File createFile(FileHeader fh, File destination) {
        File f = null;
        String name = null;
        if (fh.isFileHeader() && fh.isUnicode()) {
            name = fh.getFileNameW();
        } else {
            name = fh.getFileNameString();
        }

        f = new File(destination, name);
        if (!f.exists()) {
            try {
                f = makeFile(destination, name);
            } catch (IOException var6) {
                System.out.println(var6 + "error creating the new file: " + f.getName());
            }
        }

        return f;
    }


    private static void makeDirectory(File destination, String fileName) {
        String[] dirs = fileName.split("\\\\");
        if (dirs != null) {
            String path = "";
            String[] arr$ = dirs;
            int len$ = dirs.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                String dir = arr$[i$];
                path = path + File.separator + dir;
                (new File(destination, path)).mkdir();
            }

        }
    }
}
