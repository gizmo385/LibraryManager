package gizmo385.librarymanager.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * A dialog describing the project
 * @author cachapline8
 *
 */
public class AboutDialog extends JDialog {
	
	private JButton closeAboutDialog;
	
	/**
	 * Constructs the AboutDialog and adds its components
	 * @param frame The parent frame
	 */
	public AboutDialog( JFrame frame ) {
		
		super( frame, "About" );
		setLayout( new FlowLayout() );
		setSize( 350, 120 );
		setResizable( false );
		setLocationRelativeTo( frame );
		setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		setModalityType( ModalityType.APPLICATION_MODAL );
		setVisible( false );
		
		
		add( new JLabel( "Library Manager - Created by gizmo385" ) );
		add( new JLabel( "Source code available on Github repo" ) );
		add( new JLabel( "Programmed using the open source Eclipse IDE" ) );
		
		closeAboutDialog = new JButton( "Close" );
		closeAboutDialog.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		add( closeAboutDialog );

	}
	
	/** Inverts the visibility of the JDialog */
	public void flipVisibility() {
		setVisible( ! isVisible() );
	}
}
