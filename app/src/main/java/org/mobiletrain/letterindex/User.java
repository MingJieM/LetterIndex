package org.mobiletrain.letterindex;

/**
 * Created by wangsong on 2016/4/24.
 */
public class User {
    private int img;
    private String username;
    private String pinyin;
    private String firstLetter;

    public User() {
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String firstLetter, int img, String pinyin, String username) {
        this.firstLetter = firstLetter;
        this.img = img;
        this.pinyin = pinyin;
        this.username = username;
    }
}
