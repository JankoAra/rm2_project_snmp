package gui;

import aj210328.Main;
import aj210328.Reader;

/**
 *
 * @author janko
 */
public class StartMenu extends javax.swing.JFrame {

	public StartMenu() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		inputPeriodTextField = new javax.swing.JTextField();
		startBtn = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("RM2_Projekat_janko");

		jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
		jLabel1.setText("Izaberite period ocitavanja MIB-ova u sekundama:");
		jLabel1.setFocusable(false);

		inputPeriodTextField.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
		inputPeriodTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

		startBtn.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
		startBtn.setText("Pokreni program");
		startBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startBtnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap(150, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(jLabel1)
								.addComponent(inputPeriodTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 228,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(startBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 400,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(150, 150, 150)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(166, 166, 166).addComponent(jLabel1).addGap(134, 134, 134)
						.addComponent(inputPeriodTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 115,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(123, 123, 123).addComponent(startBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(227, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		try {
			int period = Integer.parseInt(inputPeriodTextField.getText());
			if (period <= 0) {
				period = 1;
			}
			System.out.println("Period: " + period);
			Main.switchFrame();
			new Reader(period).start();
		} catch (NumberFormatException e) {
			System.out.println("Mora biti unet ceo broj");
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(StartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(StartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(StartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(StartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new StartMenu().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JTextField inputPeriodTextField;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JButton startBtn;
	// End of variables declaration
}
