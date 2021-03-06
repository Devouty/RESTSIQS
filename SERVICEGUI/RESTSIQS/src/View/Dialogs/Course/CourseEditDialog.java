package View.Dialogs.Course;

import Beans.HTTPEntities.Course;
import Beans.NumLimitListener;
import Utils.Constant;
import Utils.HTTPJSONHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.event.*;
import java.net.ConnectException;
import java.util.HashMap;

public class CourseEditDialog extends JDialog {
    //number input limitation
    KeyListener numLimitListener = new NumLimitListener();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblCoureseName;
    private JTextField tfCourseName;
    private JPanel btnPanel;
    private JPanel contextPanel;
    private JTextField tfCredit;
    private JLabel lblCredit;
    private JLabel lblStudentId;
    private JTextField tfStudentId;
    private JLabel lblTeacherId;
    private JTextField tfTeacherId;
    private JLabel lblSum;
    private JTextField tfSum;
    private JTextField tfFinalTest;
    private JTextField tfDailyMark;
    private JLabel lblFinalTest;
    private JLabel lblDailyMark;
    private JTextField tfTest1;
    private JTextField tfTest2;
    private JTextField tfTest3;
    private JTextField tfE1;
    private JTextField tfE2;
    private JTextField tfE3;
    private JTextField tfE4;
    private JTextField tfE5;
    private JLabel blank;
    private JLabel lblTest1;
    private JLabel lblTest2;
    private JLabel lblTest3;
    private JLabel lblE1;
    private JLabel lblE2;
    private JLabel lblE3;
    private JLabel lblE4;
    private JLabel lblE5;
    private JPanel dPanel;
    private Object[][] data;
    private HashMap<String, Object> map;

    public CourseEditDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //number limit
        tfTest1.addKeyListener(numLimitListener);
        tfTest2.addKeyListener(numLimitListener);
        tfTest3.addKeyListener(numLimitListener);
        tfE1.addKeyListener(numLimitListener);
        tfE2.addKeyListener(numLimitListener);
        tfE3.addKeyListener(numLimitListener);
        tfE4.addKeyListener(numLimitListener);
        tfE5.addKeyListener(numLimitListener);
        tfFinalTest.addKeyListener(numLimitListener);
        tfDailyMark.addKeyListener(numLimitListener);
        tfCredit.addKeyListener(numLimitListener);
        tfSum.addKeyListener(numLimitListener);

    }

    public static void main(String[] args) {
        CourseEditDialog dialog = new CourseEditDialog();

        System.exit(0);
    }

    private void onOK() {
// add your code here
        if (hasEmpty()) {
            this.setTitle(Constant.ERROR_HAS_EMPTY);
        } else {
            updateCourse();
            this.setVisible(false);
            dispose();
        }
    }

    private void onCancel() {
// add your code here if necessary
        this.setVisible(false);
        dispose();
    }

    public void show(Object[][] data, HashMap<String, Object> map) {
        this.data = data;
        this.map = map;
        tfCourseName.setText((String) map.get("courseName"));
        tfCredit.setText((Double) map.get("credit") + "");
        tfDailyMark.setText((Double) map.get("dailyMark") + "");

        tfE1.setText((Double) map.get("exercises1") + "");
        tfE2.setText((Double) map.get("exercises2") + "");
        tfE3.setText((Double) map.get("exercises3") + "");
        tfE4.setText((Double) map.get("exercises4") + "");
        tfE5.setText((Double) map.get("exercises5") + "");

        tfTest1.setText((Double) map.get("test1") + "");
        tfTest2.setText((Double) map.get("test2") + "");
        tfTest3.setText((Double) map.get("test3") + "");
        tfFinalTest.setText((Double) map.get("finalTest") + "");

        tfStudentId.setText((String) map.get("studentId"));
        tfTeacherId.setText((String) map.get("teacherId"));
        tfSum.setText((Double) map.get("sum") + "");
        this.pack();
        this.setLocation(130, 150);
        this.setVisible(true);
    }

    private void updateCourse() {
        Integer dataRow = (Integer) map.get("dataRow");
        String id = (data[dataRow][17]) + "";
//        System.out.println(map.get("dataRow"));

        try {
            JSONObject jsonObject = HTTPJSONHelper.get(Constant.COURSE_URL + id);
            JSONObject requestObj = (JSONObject) ((JSONArray) (jsonObject.get("result"))).get(0);
            Course course = new Course();

            course.setCourseDate(requestObj.getString("courseDate"));
            course.setCourseId(id);
            course.setCourseName(tfCourseName.getText());
            course.setCourseTime(requestObj.getString("courseTime"));
            course.setCredit(Double.parseDouble(tfCredit.getText()));

            course.setDailyMark(Double.parseDouble(tfDailyMark.getText()));
            course.setStudentId(tfStudentId.getText());
            course.setSum(Double.parseDouble(tfSum.getText()));
            course.setFinalTest(Double.parseDouble(tfFinalTest.getText()));
            course.setTeacherId(tfTeacherId.getText());

            course.setExercises1(Double.parseDouble(tfE1.getText()));
            course.setExercises2(Double.parseDouble(tfE2.getText()));
            course.setExercises3(Double.parseDouble(tfE3.getText()));
            course.setExercises4(Double.parseDouble(tfE4.getText()));
            course.setExercises5(Double.parseDouble(tfE5.getText()));

            course.setTest1(Double.parseDouble(tfTest1.getText()));
            course.setTest2(Double.parseDouble(tfTest2.getText()));
            course.setTest3(Double.parseDouble(tfTest3.getText()));

//            String pojo2json = JSONObject.toJSONString(course);
            HTTPJSONHelper.put(Constant.COURSE_URL + "course/", course);

//            System.out.println(jsonObject.toJSONString());
        } catch (ArrayIndexOutOfBoundsException e) {
//            e.printStackTrace();
        } catch (ConnectException connectException) {
            connectException.printStackTrace();
        }
    }

    public boolean hasEmpty() {
        boolean flag = false;

        if (tfCourseName.getText().isEmpty())
            flag = true;
        if (tfCredit.getText().isEmpty())
            flag = true;
        if (tfDailyMark.getText().isEmpty())
            flag = true;
        if (tfE1.getText().isEmpty())
            flag = true;
        if (tfE2.getText().isEmpty())
            flag = true;
        if (tfE3.getText().isEmpty())
            flag = true;
        if (tfE4.getText().isEmpty())
            flag = true;
        if (tfE5.getText().isEmpty())
            flag = true;
        if (tfTest1.getText().isEmpty())
            flag = true;
        if (tfTest2.getText().isEmpty())
            flag = true;
        if (tfTest3.getText().isEmpty())
            flag = true;
        if (tfFinalTest.getText().isEmpty())
            flag = true;
        if (tfStudentId.getText().isEmpty())
            flag = true;
        if (tfTeacherId.getText().isEmpty())
            flag = true;

        return flag;
    }
}
