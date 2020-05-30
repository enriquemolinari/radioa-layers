package ar.cpfw.book.radio.main;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import ar.cpfw.book.radio.model.DefaultRadioProgram;
import ar.cpfw.book.radio.persistence.JdbcCompetitorRepository;
import ar.cpfw.book.radio.ui.InscriptionView;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Main().start();
				} catch (Exception e) {
					// log exception...
					System.out.println(e);
				}
			}
		});
	}

	private void start() throws SQLException {
		new InscriptionView(new DefaultRadioProgram(new JdbcCompetitorRepository()));
	}

}
