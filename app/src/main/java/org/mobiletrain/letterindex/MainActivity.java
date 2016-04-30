package org.mobiletrain.letterindex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<User> list;  //用来存储ListView的数据的容器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取ListView的控件
        final ListView listView = (ListView) findViewById(R.id.lv);
        //初始化ListView的数据
        initData();
        final MyAdapter adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);


        //这个textView一般是隐藏的，当右侧滑动时才显示用户当前所在的位置
        TextView textView = (TextView) findViewById(R.id.show_letter_in_center);
        //获取侧滑控件的id，该控件是自定义的控件，继承自View，通过画笔绘制出
        final LetterIndexView letterIndexView = (LetterIndexView) findViewById(R.id.letter_index_view);
        letterIndexView.setTextViewDialog(textView);
        //letterIndexView的点击和滑动事假触发事件
        letterIndexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                int positionForSection = adapter.getPositionForSection(currentChar.charAt(0));
                listView.setSelection(positionForSection);
            }
        });


        //当ListView滑动的时候，会对右侧的滑条经行修改，用红色定位当前的位置
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            /*滑动完成后回调的方法*/
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            /*滑动过程中的回调方法，监视ListView当前滑动到那个地方来了
            * */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //传入一个position，获得该position所在的分组,
                // 返回position位置的名字的拼音的首字母
                int sectionForPosition = adapter.getSectionForPosition(firstVisibleItem);
                //对右侧滑动条的样式进行更新
                letterIndexView.updateLetterIndexView(sectionForPosition);
            }
        });
    }

    /*该方法用来加载数据，这里的数据是从res/values/string目录中取得。
    * 以后型这样的数据可以在数据库中或者从文件中去获取*/
    private void initData() {
        list = new ArrayList<>();
        //1.获取姓名数据（汉字）
        String[] allUserNames = getResources().getStringArray(R.array.arrUsernames);
        //2.将数组的每一条数据存放到USerBean中
        for (String allUserName : allUserNames) {
            User user = new User();
            user.setUsername(allUserName);
            //利用ChineseToPinyinHelper将中文转化成拼音
            String convert = ChineseToPinyinHelper.getInstance().getPinyin(allUserName).toUpperCase();
            user.setPinyin(convert);
            //取出姓名的拼音的个字符
            String substring = convert.substring(0, 1);
            if (substring.matches("[A-Z]")) { //利用了正则表达式，字符为字母
                user.setFirstLetter(substring);
            }else{
                user.setFirstLetter("#");
            }
            list.add(user); //添加到LIst中
        }

        //对List的数据按字母进行排序
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                if (lhs.getFirstLetter().contains("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().contains("#")) {
                    return -1;
                }else{
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });
    }
}
