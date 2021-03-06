package View.Dialogs.Student;

import Beans.EditButtonRenderer;
import Beans.HTTPEntities.Student;
import Beans.NumLimitListener;
import Utils.Constant;
import Utils.FitJTableHeaderUtil;
import Utils.HTTPJSONHelper;
import View.Dialogs.Course.CourseDeleteDialog;
import View.Dialogs.Course.CourseEditDialog;
import View.Dialogs.Tecnologicalexam.TEAddDialog;
import View.Dialogs.Tecnologicalexam.TEDeleteDialog;
import View.Dialogs.Tecnologicalexam.TEEditDialog;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.net.ConnectException;
import java.util.HashMap;

public class StudentEditDialog extends JDialog {
    Object[][] d;
    HashMap<String, Object> map;
    EditButtonRenderer studentTableBtnEdit, studentTableBtnDelete;
    CourseDeleteDialog courseDeleteDialog;
    CourseEditDialog courseEditDialog;
    Object[][] data, data2;
    TEEditDialog teEditDialog;
    TEDeleteDialog teDeleteDialog;
    TEAddDialog teAddDialog;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel studentMessagePanel;
    private JPanel studentCoursePanel;
    private JPanel studentTEPanel;
    private JTable studentCourseTable;
    private JTable studentTETable;
    private JTextField tfStudentPassword;
    private JTextField tfSex;
    private JTextField tfIdentityCard;
    private JTextField tfBankCard;
    private JTextField tfAcademyId;
    private JTextField tfStudentId;
    private JButton btnAddCourse;
    private JButton btnAddTecnologicalExam;

    public StudentEditDialog() {
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
        btnAddTecnologicalExam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("studentId", StudentEditDialog.this.map.get("studentId"));
                int newId = 0;
                for (int i = 0; i < data2.length; i++) {
                    if (((Double) data2[i][2]) > newId) {
                        newId = ((Double) data2[i][2]).intValue();
                    }
                }
                newId++;
                map.put("newId", newId);
                teAddDialog.show(map);
            }
        });
        btnAddCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        FitJTableHeaderUtil.fitTableColumns(studentCourseTable);
        FitJTableHeaderUtil.fitTableColumns(studentTETable);
    }

    public static void main(String[] args) {
        StudentEditDialog dialog = new StudentEditDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
// add your code here
        if (!hasEmpty()) {
            Student student = new Student();
            student.setStudentPassword(tfStudentPassword.getText());
            student.setSex(tfSex.getText());
            student.setAcademyId(tfAcademyId.getText());
            student.setIdentityCard(tfIdentityCard.getText());
            student.setBankCard(tfBankCard.getText());
            student.setStudentId(tfStudentId.getText());
            try {
                HTTPJSONHelper.put(Constant.STUDENT_URL + "student/", student);
                dispose();
            } catch (ConnectException e) {
                e.printStackTrace();
            }

        } else {
            StudentEditDialog.this.setTitle(Constant.ERROR_HAS_EMPTY);
        }

    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public void show(Object[][] data, HashMap<String, Object> map) {
        this.d = data;
        this.map = map;
        tfAcademyId.setText((String) map.get("academyId"));
        tfBankCard.setText((String) map.get("bankCard"));
        tfIdentityCard.setText((String) map.get("identityCard"));
        tfSex.setText((String) map.get("sex"));
        tfStudentId.setText((String) map.get("studentId"));
        tfStudentPassword.setText((String) map.get("studentPassword"));
        studentTableBtnEdit = new EditButtonRenderer(Constant.TABLE_BUTTUN_EDIT);
        studentTableBtnDelete = new EditButtonRenderer(Constant.TABLE_BUTTUN_DELETE);

        courseEditDialog = new CourseEditDialog();
        courseDeleteDialog = new CourseDeleteDialog();
        teDeleteDialog = new TEDeleteDialog();
        teEditDialog = new TEEditDialog();
        teAddDialog = new TEAddDialog();
        readTable();
        this.setBounds(230, 150, 650, 450);
        this.setVisible(true);
        NumLimitListener numLimitListener = new NumLimitListener();
        tfStudentId.addKeyListener(numLimitListener);
        tfBankCard.addKeyListener(numLimitListener);
        tfIdentityCard.addKeyListener(numLimitListener);

    }

    private boolean updateStudent() {
        Student student = new Student();
        student.setAcademyId(tfAcademyId.getText());
        student.setStudentId(tfStudentId.getText());
        student.setBankCard(tfBankCard.getText());
        student.setIdentityCard(tfIdentityCard.getText());
        student.setSex(tfSex.getText());
        student.setStudentPassword(tfStudentPassword.getText());
        try {
            HTTPJSONHelper.put(Constant.STUDENT_URL + "student/", student);
            return true;
        } catch (ConnectException e) {
            e.printStackTrace();
            StudentEditDialog.this.setTitle(Constant.ERROR_CONNECTION_FAILED);
        }
        return false;
    }

    private boolean hasEmpty() {
        boolean flag = false;

        if (tfStudentId.getText().isEmpty())
            flag = true;
        if (tfStudentPassword.getText().isEmpty())
            flag = true;
        if (tfSex.getText().isEmpty())
            flag = true;
        if (tfIdentityCard.getText().isEmpty())
            flag = true;
        if (tfAcademyId.getText().isEmpty())
            flag = true;
        if (tfBankCard.getText().isEmpty())
            flag = true;

        return flag;
    }

    public void readTable() {

        updateData();

        studentCourseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (studentCourseTable.getSelectedColumn() == 0) {
                    int y = studentCourseTable.getSelectedRow();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("credit", data[y][2]);
                    map.put("teacherId", data[y][3]);
                    map.put("studentId", data[y][4]);
                    map.put("courseName", data[y][5]);
                    map.put("sum", data[y][6]);

                    map.put("finalTest", data[y][7]);
                    map.put("dailyMark", data[y][8]);
                    map.put("test1", data[y][9]);
                    map.put("test2", data[y][10]);
                    map.put("test3", data[y][11]);

                    map.put("exercises1", data[y][12]);
                    map.put("exercises2", data[y][13]);
                    map.put("exercises3", data[y][14]);
                    map.put("exercises4", data[y][15]);
                    map.put("exercises5", data[y][16]);
                    map.put("dataRow", studentCourseTable.getSelectedRow());
                    courseEditDialog.show(data, map);

                    updateData();

                }
                if (studentCourseTable.getSelectedColumn() == 1) {
                    courseDeleteDialog.show((String) data[studentCourseTable.getSelectedRow()][2], (String) data[studentCourseTable.getSelectedRow()][7]);
                    updateData();
                }
                if (e.getClickCount() == 2) {
                    int y = studentCourseTable.getSelectedRow();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("credit", data[y][2]);
                    map.put("teacherId", data[y][3]);
                    map.put("studentId", data[y][4]);
                    map.put("courseName", data[y][5]);
                    map.put("sum", data[y][6]);

                    map.put("finalTest", data[y][7]);
                    map.put("dailyMark", data[y][8]);
                    map.put("test1", data[y][9]);
                    map.put("test2", data[y][10]);
                    map.put("test3", data[y][11]);

                    map.put("exercises1", data[y][12]);
                    map.put("exercises2", data[y][13]);
                    map.put("exercises3", data[y][14]);
                    map.put("exercises4", data[y][15]);
                    map.put("exercises5", data[y][16]);
                    map.put("dataRow", studentCourseTable.getSelectedRow());
                    courseEditDialog.show(data, map);
                    updateData();
                }
            }
        });
        /*
           tId                  varchar(255) not null,
           tName                varchar(255) default "无",
           tDate                varchar(255) default "无",
           tSorce               double default 0,
           studentId		varchar(255) default "无",
         */
        studentTETable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (studentTETable.getSelectedColumn() == 0) {
                    int y = studentTETable.getSelectedRow();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("tId", data2[y][2]);
                    map.put("tName", data2[y][3]);
                    map.put("tDate", data2[y][4]);
                    map.put("tSorce", data2[y][5]);
                    map.put("studentId", data2[y][6]);
                    teEditDialog.show(map);
                    updateData();
                }
                if (studentTETable.getSelectedColumn() == 1) {
                    teDeleteDialog.show((String) data2[studentTETable.getSelectedRow()][2], (String) data2[studentTETable.getSelectedRow()][3]);
                    updateData();
                }
                if (e.getClickCount() == 2) {
                    int y = studentTETable.getSelectedRow();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("tId", data2[y][2]);
                    map.put("tName", data2[y][3]);
                    map.put("tDate", data2[y][4]);
                    map.put("tSorce", data2[y][5]);
                    map.put("studentId", data2[y][6]);
                    teEditDialog.show(map);
                    updateData();
                }
            }
        });
    }

    private void updateData() {
        JSONObject courseJSONObject = null, TEJSONObject = null;
        try {
            courseJSONObject = HTTPJSONHelper.get(Constant.COURSE_URL + "student/" + this.map.get("studentId"));
            TEJSONObject = HTTPJSONHelper.get(Constant.TECNOLOGICALEXAM_URL + "student/" + this.map.get("studentId"));
        } catch (ConnectException e) {
            StudentEditDialog.this.setTitle(Constant.ERROR_CONNECTION_FAILED);
        }
        JSONArray jsonArray = (JSONArray) courseJSONObject.get("result");
        int length = jsonArray.size();
        JSONObject obj;
        data = null;
        data = new Object[length][18];
        for (int i = 0; i < length; i++) {
            obj = (JSONObject) jsonArray.get(i);
            data[i][0] = Constant.TABLE_BUTTUN_EDIT;
            data[i][1] = Constant.TABLE_BUTTUN_DELETE;

            data[i][2] = obj.getDouble("credit");
            data[i][3] = obj.getString("teacherId");
            data[i][4] = obj.getString("studentId");
            data[i][5] = obj.getString("courseName");
            data[i][6] = obj.getDouble("sum");

            data[i][7] = obj.getDouble("finalTest");
            data[i][8] = obj.getDouble("dailyMark");
            data[i][9] = obj.getDouble("test1");
            data[i][10] = obj.getDouble("test2");
            data[i][11] = obj.getDouble("test3");

            data[i][12] = obj.getDouble("exercises1");
            data[i][13] = obj.getDouble("exercises2");
            data[i][14] = obj.getDouble("exercises3");
            data[i][15] = obj.getDouble("exercises4");
            data[i][16] = obj.getDouble("exercises5");

            data[i][17] = obj.getString("courseId");

        }
        Object[] names = {
                Constant.TABLE_BUTTUN_EDIT,
                Constant.TABLE_BUTTUN_DELETE,

                "credit",
                "teacherId",
                "studentId",
                "courseName",
                "sum",

                "finalTest",
                "dailyMark",
                "test1",
                "test2",
                "test3",

                "exercises1",
                "exercises2",
                "exercises3",
                "exercises4",
                "exercises5",
                "courseId"};

        DefaultTableModel model = new DefaultTableModel(data, names) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            studentCourseTable.setModel(model);
        } catch (ArrayIndexOutOfBoundsException e) {
            StudentEditDialog.this.setTitle("");
        }
        studentCourseTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        studentCourseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentCourseTable.getColumn(Constant.TABLE_BUTTUN_EDIT).setCellRenderer(studentTableBtnEdit);
        studentCourseTable.getColumn(Constant.TABLE_BUTTUN_DELETE).setCellRenderer(studentTableBtnDelete);
        studentCourseTable.setDoubleBuffered(false);
            /*
            tId                  varchar(255) not null,
            tName                varchar(255) default "��",
            tDate                varchar(255) default "��",
            tSorce               double default 0,
            studentId		varchar(255) default "��",
             */
        jsonArray = (JSONArray) TEJSONObject.get("result");
        length = jsonArray.size();

        data2 = null;
        data2 = new Object[length][7];
        for (int i = 0; i < length; i++) {
            obj = (JSONObject) jsonArray.get(i);
            data2[i][0] = Constant.TABLE_BUTTUN_EDIT;
            data2[i][1] = Constant.TABLE_BUTTUN_DELETE;

            data2[i][2] = obj.getDouble("tid").intValue();
            data2[i][3] = obj.getString("tname");
            data2[i][4] = obj.getString("tdate");
            data2[i][5] = obj.getString("tsorce");
            data2[i][6] = obj.getDouble("studentId").intValue();
        }
        Object[] tNames = {
                Constant.TABLE_BUTTUN_EDIT,
                Constant.TABLE_BUTTUN_DELETE,

                "tId",
                "tName",
                "tDate",
                "tSorce",
                "studentId"};

        DefaultTableModel model2 = new DefaultTableModel(data2, tNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            studentTETable.setModel(model2);
        } catch (ArrayIndexOutOfBoundsException e) {
            StudentEditDialog.this.setTitle("");
        }
        studentTETable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        studentTETable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTETable.getColumn(Constant.TABLE_BUTTUN_EDIT).setCellRenderer(studentTableBtnEdit);
        studentTETable.getColumn(Constant.TABLE_BUTTUN_DELETE).setCellRenderer(studentTableBtnDelete);
        studentTETable.setDoubleBuffered(false);
    }
}
