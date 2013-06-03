import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.StringTokenizer;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Convert {

    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException, NotSupportedException {        
        String rootName = "粵語聖經";
        String path =  Convert.class.getResource(rootName).getPath();  
        String destPath =  Convert.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        
        System.out.println(destPath);
        path = URLDecoder.decode(path, "utf-8");
        File root = new File(path);
        String[] testamentFolders = root.list();

        for (int i=0; i<testamentFolders.length; i++) {
            File testament =  new File(path + "/" + testamentFolders[i]); 
            String[] bookFolders = testament.list();

            for (int j=0; j<bookFolders.length; j++) {                
                File book =  new File(path + "/" + testamentFolders[i] + "/" + bookFolders[j]);
                String[] chapterFolders = book.list();

                for (int k=0; k<chapterFolders.length; k++) {   
                    String p = path + "/" + testamentFolders[i] + "/" + bookFolders[j] + "/" + chapterFolders[k];
                    System.out.println(p);   

                    Mp3File mp3file = new Mp3File(p);
                    ID3v2 id3v2Tag;
                    if (mp3file.hasId3v2Tag()) {
                        id3v2Tag = mp3file.getId3v2Tag();
                    } else {
                        id3v2Tag = new ID3v23Tag();
                        mp3file.setId3v2Tag(id3v2Tag);
                    }
                    id3v2Tag.setTrack(String.valueOf(k+1));
                    id3v2Tag.setArtist("粵語聖經");
                    id3v2Tag.setTitle(getS(chapterFolders[k]));
                    id3v2Tag.setAlbum(getS(bookFolders[j]));
                    
                    
                    boolean b = new File(destPath + "new_" + rootName + "/" + testamentFolders[i] + "/" + bookFolders[j]).mkdirs();
                    mp3file.save(destPath + "new_" + rootName + "/" + testamentFolders[i] + "/" + bookFolders[j] + "/" + chapterFolders[k]);
                }
            }
            
        }
    }
    
    public static String getS(String s) {
        StringTokenizer st = new StringTokenizer(s, "_");
        String token = st.nextToken();        
        return st.nextToken(); 
    }
}
