import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Tagger02 {

    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException, NotSupportedException {        
        String rootName = "粵語聖經02";
        String path =  Tagger02.class.getResource(rootName).getPath();  
        String destPath =  Tagger02.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        
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
                    id3v2Tag.setArtist("粵語聖經02");
                    id3v2Tag.setTitle(getTitle(chapterFolders[k]));
                    id3v2Tag.setAlbum(getAblum(bookFolders[j]));
                    
                    boolean b = new File(destPath + "new_" + rootName + "/" + testamentFolders[i] + "/" + bookFolders[j]).mkdirs();
                    mp3file.save(destPath + "new_" + rootName + "/" + testamentFolders[i] + "/" + bookFolders[j] + "/" + chapterFolders[k]);
                }
            }
            
        }
    }
    
    public static String getAblum(String s) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("matthew", "馬太福音");
        m.put("mark", "馬可福音");
        m.put("luke", "路加福音");
        m.put("john", "約翰福音");
        m.put("acts", "使徒行傳");
        m.put("romans", "羅馬書");
        m.put("1-corinthians", "哥林多前書");
        m.put("2-corinthians", "哥林多後書");
        m.put("galatians", "加拉太書");
        m.put("ephesians", "以弗所書");
        m.put("philippians", "腓立比書");
        m.put("colossians", "歌羅西書");
        m.put("1-thessalonians", "帖撒羅尼迦前書");
        m.put("2-thessalonians", "帖撒羅尼迦後書");
        m.put("1-timothy", "提犘太前書");
        m.put("2-timothy", "提犘太後書");
        m.put("titus", "提多書");
        m.put("philemon", "腓利門書");
        m.put("hebrews", "希伯來書");
        m.put("james", "雅各書");
        m.put("1-peter", "彼得前書");
        m.put("2-peter", "彼得後書");
        m.put("1-john", "約翰一書");
        m.put("2-john", "約翰二書");
        m.put("3-john", "約翰三書");
        m.put("jude", "猶大書");
        m.put("revelation", "啟示錄");
        
        StringTokenizer st = new StringTokenizer(s, "_");
        String token = st.nextToken();        
        return m.get(st.nextToken()); 
    }
    
    public static String getTitle(String s1) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("matthew", "馬太福音");
        m.put("mark", "馬可福音");
        m.put("luke", "路加福音");
        m.put("john", "約翰福音");
        m.put("acts", "使徒行傳");
        m.put("romans", "羅馬書");
        m.put("1-corinthians", "哥林多前書");
        m.put("2-corinthians", "哥林多後書");
        m.put("galatians", "加拉太書");
        m.put("ephesians", "以弗所書");
        m.put("philippians", "腓立比書");
        m.put("colossians", "歌羅西書");
        m.put("1-thessalonians", "帖撒羅尼迦前書");
        m.put("2-thessalonians", "帖撒羅尼迦後書");
        m.put("1-timothy", "提犘太前書");
        m.put("2-timothy", "提犘太後書");
        m.put("titus", "提多書");
        m.put("philemon", "腓利門書");
        m.put("hebrews", "希伯來書");
        m.put("james", "雅各書");
        m.put("1-peter", "彼得前書");
        m.put("2-peter", "彼得後書");
        m.put("1-john", "約翰一書");
        m.put("2-john", "約翰二書");
        m.put("3-john", "約翰三書");
        m.put("jude", "猶大書");
        m.put("revelation", "啟示錄");
        
        StringTokenizer st = new StringTokenizer(s1, "_.");
        String id = st.nextToken();  
        String book = st.nextToken();  
        String chapterSeq = st.nextToken(); 
        if (!chapterSeq.startsWith("0")) {
            chapterSeq = "01";
        }
        return m.get(book)+chapterSeq+"章"; 
    }
}
