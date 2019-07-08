package gradle_jdbc_study.ui.list;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public abstract class AbstractListPanel<T> extends JPanel {
	private JTable table;
	protected List<T> itemList;
	private JScrollPane scrollPane;
	
	public AbstractListPanel(String title) {
		initComponents(title);
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	private void initComponents(String title) {
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400, 200));
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		table.setComponentPopupMenu(popupMenu);
		scrollPane.setComponentPopupMenu(popupMenu);
	}
	
	public void reloadData() {
		table.setModel(new DefaultTableModel(getRows(), getColumnNames()));
		tableAlignmentAndWidth();
	}

	/**
	 * 	부서번호, 부서명은 가운데 정렬
		tableCellAlignment(SwingConstants.CENTER, 0, 1);
		위치(층)은 우측 정렬
		tableCellAlignment(SwingConstants.RIGHT, 2);
		부서번호, 부서명, 위치 의 폭을 (100, 200, 70)으로 가능하면 설정
		tableSetWidth(100, 200, 70);
	 */
	protected abstract void tableAlignmentAndWidth();

	private Object[][] getRows() {
		Object[][] rows = new Object[itemList.size()][];
		for (int i = 0; i < itemList.size(); i++) {
			rows[i] = toArray(i);
		}
		return rows;
	}

	protected abstract Object[] toArray(int idx);
	
	/**
	 * @return new String[] { "부서번호", "부서명", "위치(층)" };
	 */
	protected abstract String[] getColumnNames();

	// 테이블 셀 내용의 정렬
	protected void tableCellAlignment(int align, int... idx) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);

		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < idx.length; i++) {
			model.getColumn(idx[i]).setCellRenderer(dtcr);
		}
	}

	// 테이블 셀의 폭 설정
	protected void tableSetWidth(int... width) {
		TableColumnModel cModel = table.getColumnModel();

		for (int i = 0; i < width.length; i++) {
			cModel.getColumn(i).setPreferredWidth(width[i]);
		}
	}


	public T getSelectedItem() {
		int i = table.getSelectedRow();
		T item = null;
		if (table.getModel().getRowCount() == 0) { // 부서정보가 존재하지 않을 경우
			return item;
		}
		if (i < 0 || i > table.getModel().getRowCount() - 1) { // 선택하지 않은 경우
			JOptionPane.showMessageDialog(null, "선택된 부서가 없습니다.");
			return item;
		}

		item = itemList.get(i);
		return item;
	}
}
