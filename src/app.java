
import java.awt.EventQueue;
public class app {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginPage login = new loginPage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
