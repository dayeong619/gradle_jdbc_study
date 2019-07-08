package gradle_jdbc_study.ui.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import gradle_jdbc_study.dto.Title;

@SuppressWarnings("serial")
public class PanelTitle extends AbstractPanelContent<Title> {
	private JTextField tfTitleNo;
	private JTextField tfTitleName;
	
	public PanelTitle() {
		initComponents();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "부서정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 10));
		
		JLabel lblTitleNo = new JLabel("부서 번호");
		lblTitleNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitleNo);
		
		tfTitleNo = new JTextField();
		add(tfTitleNo);
		tfTitleNo.setColumns(10);
		
		JLabel lblTitleName = new JLabel("부서명");
		lblTitleName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitleName);
		
		tfTitleName = new JTextField();
		tfTitleName.setColumns(10);
		add(tfTitleName);
	}
	
	@Override
	public void setItem(Title Title) {
		tfTitleNo.setText(String.format("T%03d", Title.getTitleNo()));
		tfTitleName.setText(Title.getTitleName());		
	}

	@Override
	public Title getItem() {
		int titleNo = Integer.parseInt(tfTitleNo.getText().trim().substring(1));
		String titleName = tfTitleName.getText().trim();
		return new Title(titleNo, titleName);
	}

	@Override
	public void clearComponent(int nextNo) {
		tfTitleNo.setText(String.format("T%03d", nextNo));
		tfTitleName.setText("");
		tfTitleNo.setEditable(false);
	}

	@Override
	public JTextField getTfNo() {
		return tfTitleNo;
	}

	@Override
	public void setComponentAllEditable(boolean isEditable) {
		tfTitleNo.setEditable(isEditable);
		tfTitleName.setEditable(isEditable);		
	}

}
