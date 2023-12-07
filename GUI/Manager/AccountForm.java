package GUI.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Back.Datarelated.User;
import Back.Datarelated.UserDataSet;
import GUI.Manager.Popup.UserUpdatePopup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.regex.PatternSyntaxException;
 
public class AccountForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDataSet users;
    JScrollPane scrollPane;
    ImageIcon icon;
    private ManagerForm owner;
    private JButton btnChange;
    private JButton btnSearch;
    private JButton btnDelete;
    private JButton btnClose;
    private JTextField tfSearchId;
    private JScrollPane tablescroll;
    private JTable taIdlist;
    private DefaultTableModel DTmodel;
    private String header[]= {"순번","아이디","비밀번호","닉네임"};
    private TableRowSorter<DefaultTableModel> sorter;
    public AccountForm(ManagerForm owner) {
    	this.owner = owner;
    	users = new UserDataSet();
        icon = new ImageIcon(AccountForm.class.getResource("/image/background/accountbg.png"));
       
        
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        JPanel bg = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        
        bg.setLayout(null);
        init();
        bg.add(btnChange);
        bg.add(btnDelete);
        bg.add(btnSearch);
        bg.add(btnClose);
        bg.add(tfSearchId);
        tablescroll.setViewportView(taIdlist);
        bg.add(tablescroll);
        
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        
        addListeners();
        showFrame();
    }
    public void init() {
    	Font font = new Font("SansSerif", 1, 20);
        
        tfSearchId = new JTextField();
        tfSearchId.setFont(font);
        tfSearchId.setBounds(557, 101, 155, 28);
        tfSearchId.setBorder(null);
        
        btnChange = new JButton();
		btnChange.setBorderPainted(false);
		btnChange.setContentAreaFilled(false);
        btnChange.setBounds(249, 475, 128, 50);
        
        btnDelete = new JButton();
		btnDelete.setBorderPainted(false);
		btnDelete.setContentAreaFilled(false);
        btnDelete.setBounds(405, 475, 128, 50);
        
        btnSearch = new JButton();
		btnSearch.setBorderPainted(false);
		btnSearch.setContentAreaFilled(false);
        btnSearch.setBounds(713, 103, 25, 25);
        
        btnClose = new JButton();
		btnClose.setBorderPainted(false);
		btnClose.setContentAreaFilled(false);
        btnClose.setBounds(700, 35, 50, 50);
        
        
        List<User> list = users.UserList();
        DTmodel = (new DefaultTableModel(header, 0) {
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
        	      //all cells false
        	      return false;
        	    }
        	});
        for(int i=0; i<list.size(); i++) {
        	Object[] data = {i+1, list.get(i).getId(),
        			list.get(i).getPw(), list.get(i).getNickName()};
        	DTmodel.addRow(data);
        }
        taIdlist = new JTable(DTmodel);
        taIdlist.setFont(font);
        //taIdlist.setBounds(42, 178, 698, 282);
        taIdlist.getColumn(header[0]).setPreferredWidth(48);
        taIdlist.getColumn(header[1]).setPreferredWidth(220);
        taIdlist.getColumn(header[2]).setPreferredWidth(219);
        taIdlist.getColumn(header[3]).setPreferredWidth(220);
        taIdlist.setRowHeight(23);
        sorter = new TableRowSorter<>(DTmodel);
        taIdlist.setRowSorter(sorter);
        
        tablescroll = new JScrollPane();
        tablescroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tablescroll.setBounds(42, 138, 698, 322);
    }
    public void showFrame() {
        setTitle("픽셀 어드벤쳐");
        setSize(800,600);
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);//창 크기 고정
        setVisible(true);
    }

    public void addListeners() {

        btnChange.addActionListener(new ActionListener() {
        	Object value;
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		value = taIdlist.getValueAt(taIdlist.getSelectedRow(), 1);
            	}catch(Exception ex) {
            		value = null;
            	}            	
            	if(value == null) {
            		JOptionPane.showMessageDialog(
                    		AccountForm.this,
                            "선택한 계정이 없습니다."
                    );
            	}else {
            		new UserUpdatePopup(AccountForm.this);
            	}
                    
            }
        });
        btnDelete.addActionListener(new ActionListener() {
        	Object value;
            @Override           
            public void actionPerformed(ActionEvent e) {
            	try {
            		value = taIdlist.getValueAt(taIdlist.getSelectedRow(), 1);
            	}catch(Exception ex) {
            		value = null;
            	}
            	if(value == null) {
            		JOptionPane.showMessageDialog(
                    		AccountForm.this,
                            "선택한 계정이 없습니다."
                    );
            	}else {
            		int choice = JOptionPane.showConfirmDialog(
                    		AccountForm.this,
                            "선택한 계정을 삭제하시겠습니까?",
                            "삭제",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (choice == JOptionPane.OK_OPTION) {
                    	DTmodel.removeRow(taIdlist.convertRowIndexToModel(taIdlist.getSelectedRow()));
                    	users.deleteUsers(value.toString());
                    }
            	}
                
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            
            public void actionPerformed(ActionEvent e) {
            	 if (tfSearchId.getText().isEmpty()) {
                     JOptionPane.showMessageDialog(AccountForm.this,
                             "검색할 아이디를 입력하시오.",
                             "아이디 입력",
                             JOptionPane.WARNING_MESSAGE);
                     search();
                    
                 }else if(users.isIdOverlap(tfSearchId.getText())){                         	                	 
                	 search();
                	 tfSearchId.setText("");
                 }
                 else {
                     JOptionPane.showMessageDialog(
                      		AccountForm.this,
                              "없는 아이디 입니다."
                      );    
                     tfSearchId.setText("");
                     search();
                 }
            	 
            	 
            }
        });
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		AccountForm.this,
                        "계정 관리를 종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                	dispose();
                	owner.setVisible(true);
                }
            	
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		AccountForm.this,
                        "종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    private void search() {
        String searchText = tfSearchId.getText().trim();

        // 검색어가 없으면 모든 행을 다시 보여준다.
        if (searchText.isEmpty()) {
           sorter.setRowFilter(null);
           
        } else {
         	try {
         		sorter.setRowFilter(RowFilter.regexFilter("^"+searchText+"$", 1));
          	} catch (PatternSyntaxException ex) {
              	// 검색어가 정규식으로 변환되지 않으면 모든 행을 보여준다.
               sorter.setRowFilter(null);
          	}
      	}
   }
    public String getId(){
    	Object value;
    	value = taIdlist.getValueAt(taIdlist.getSelectedRow(), 1);
    	return value.toString();
    }
    
    public String getPw(){
    	Object value;
    	value = taIdlist.getValueAt(taIdlist.getSelectedRow(), 2);
    	return value.toString();
    }
    
    public String getNickname(){
    	Object value;
    	value = taIdlist.getValueAt(taIdlist.getSelectedRow(), 3);
    	return value.toString();
    }
    public DefaultTableModel getDTmodel() {
    	return this.DTmodel;
    }
    public JTable getJtable() {
    	return this.taIdlist;
    }
}
