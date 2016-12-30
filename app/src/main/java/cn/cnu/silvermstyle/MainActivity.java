package cn.cnu.silvermstyle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    private String[] answer = new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",};//用以存放选项a或b
    //每道题的题目
    private String[] title = {"我办事喜欢", "如何我是一名教师，我比较喜欢教", "我发现比较容易学习的是",
            "在阅读非小说类作品时，我偏爱", "我比较喜欢", "我更喜欢被认为是：", "当我阅读趣闻时, 我喜欢作者",
            "当我执行一项任务是，我喜欢", " 当我要赞扬他人时，我说他是 ", "我喜欢的课程内容主要是",
            "当我长时间地从事计算工作时"};
    //每道题的a选项
    private String[] option_a = {"讲究实际", "关于事实和实际情况的课程", "事实性内容",
            "那些能告诉我新事实和教我怎么做的东西", "确定性的想法", "对工作细节很仔细", "以开门见山的方式叙述",
            "掌握一种方法", "很敏感的", "具体材料(事实、数据)", "我喜欢重复我的步骤并仔细地检查我的工作",};
    //每道题的b选项
    private String[] option_b = {"标新立异", "关于思想和理论方面的课程", "概念性内容", "那些能启发我思考的东西",
            "推论性的想法", "对工作很有创造力", "以新颖有趣的方式叙述", "想出多种方法",
            "想象力丰富的", "抽象材料 (概念、理论)", "我认为检查工作非常无聊，我是在逼迫自己这么干"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        RadioAdapter adapter = new RadioAdapter();
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }

    //提交
    public void submit(View view) {
        int ANSWER_A = 0;//用以记录答案为a,b的个数
        int ANSWER_B = 0;
        int RESULT_number = 0;
        String RESULT_alpha = "";
        int sum = 0;//用以计算a的个数和b的个数总和，sum为 title.length时说明每个题都做过了
        for (int i = 0; i < title.length; i++) {
            if (answer[i].equals("a")) ANSWER_A++;
            else if (answer[i].equals("b")) ANSWER_B++;
        }
        sum += ANSWER_A + ANSWER_B;
        if (sum == title.length) {
            System.out.println("答案a的个数是：" + ANSWER_A);
            System.out.println("答案b的个数是：" + ANSWER_B);
            if (ANSWER_A > ANSWER_B) {
                RESULT_number = ANSWER_A - ANSWER_B;
                RESULT_alpha = "a";
            } else {
                RESULT_number = ANSWER_B - ANSWER_A;
                RESULT_alpha = "b";
            }
            System.out.println("该学生的结果为" + RESULT_number + RESULT_alpha);
            if (RESULT_alpha.equals("a")) {
                System.out.println("感悟型");
            }
            else System.out.println("直觉型");
           //跳转到测试结果页面
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("int", RESULT_number);
            intent.putExtra("string", RESULT_alpha);
            startActivity(intent);
        } else {
            System.out.println("答案a的个数是：" + ANSWER_A + "答案b的个数是：" + ANSWER_B);
            new AlertDialog.Builder(MainActivity.this).setMessage("请填写完整").show();
        }
    }

    private class RadioAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            RadioAdapter.viewHolder holder = new viewHolder();

            View v = null;
            //判断条目是否有缓存
            if (convertView == null) {
                //把布局文件填充成一个View对象
                v = View.inflate(MainActivity.this, R.layout.listview_style, null);
            } else {
                v = convertView;
            }
            holder.textView = (TextView) v.findViewById(R.id.textView);
            holder.radioButton1 = (RadioButton) v.findViewById(R.id.radioButton);
            holder.radioButton2 = (RadioButton) v.findViewById(R.id.radioButton2);

            holder.textView.setText(title[position]);//设置题目
            holder.radioButton1.setText(option_a[position]);//设置选项a
            holder.radioButton2.setText(option_b[position]);//设置选项b
            holder.radioButton1//radioButton1的点击事件
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                answer[position] = "a";//如果选中第一个选项就把数组中相应的位置改为a
                            }

                        }
                    });
            holder.radioButton2//radioButton2的点击事件
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (isChecked) {
                                answer[position] = "b";
                            }
                        }
                    });
            return v;
        }

        class viewHolder {
            TextView textView;
            RadioButton radioButton1;
            RadioButton radioButton2;
        }
    }

}
