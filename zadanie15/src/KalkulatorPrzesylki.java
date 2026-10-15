import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KalkulatorPrzesylki extends JFrame {
    private JTextField tfWaga;
    private JComboBox<String> cbGabaryt;
    private JRadioButton rbStandard, rbEkspres;
    private JCheckBox chcUbezpieczenie;
    private JButton btnOblicz;
    private JLabel lblWynik;

    public KalkulatorPrzesylki() {
        setTitle("INF.04 - Kalkulator Przesyłki");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(7, 1, 10, 10));

        JPanel pWaga = new JPanel();
        pWaga.add(new JLabel("Waga przesyłki (kg):"));
        tfWaga = new JTextField(10);
        pWaga.add(tfWaga);
        add(pWaga);

        JPanel pGabaryt = new JPanel();
        pGabaryt.add(new JLabel("Gabaryt:"));
        cbGabaryt = new JComboBox<>(new String[]{
                "Gabaryt A (do 8 cm)",
                "Gabaryt B (do 19 cm)",
                "Gabaryt C (do 41 cm)"
        });
        pGabaryt.add(cbGabaryt);
        add(pGabaryt);

        JPanel pDostawa = new JPanel();
        rbStandard = new JRadioButton("Dostawa Standardowa", true);
        rbEkspres = new JRadioButton("Dostawa Ekspresowa");
        ButtonGroup bgDostawa = new ButtonGroup();
        bgDostawa.add(rbStandard);
        bgDostawa.add(rbEkspres);
        pDostawa.add(rbStandard);
        pDostawa.add(rbEkspres);
        add(pDostawa);

        JPanel pUbezpieczenie = new JPanel();
        chcUbezpieczenie = new JCheckBox("Ubezpieczenie przesyłki (+10 zł)");
        pUbezpieczenie.add(chcUbezpieczenie);
        add(pUbezpieczenie);

        JPanel pPrzycisk = new JPanel();
        btnOblicz = new JButton("Oblicz Koszt");
        pPrzycisk.add(btnOblicz);
        add(pPrzycisk);

        JPanel pWynik = new JPanel();
        lblWynik = new JLabel("Koszt przesyłki: 0,00 zł");
        pWynik.add(lblWynik);
        add(pWynik);

        btnOblicz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wagaTekst = tfWaga.getText().trim();
                if (wagaTekst.isEmpty()) {
                    JOptionPane.showMessageDialog(KalkulatorPrzesylki.this, "Pole wagi nie może być puste!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double waga;
                try {
                    waga = Double.parseDouble(wagaTekst.replace(',', '.'));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(KalkulatorPrzesylki.this, "Waga musi być poprawną liczbą!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (waga <= 0) {
                    JOptionPane.showMessageDialog(KalkulatorPrzesylki.this, "Waga musi być większa od 0!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double kosztBazowy = 0;
                int wybranyGabaryt = cbGabaryt.getSelectedIndex();
                if (wybranyGabaryt == 0) {
                    kosztBazowy = 10;
                } else if (wybranyGabaryt == 1) {
                    kosztBazowy = 15;
                } else if (wybranyGabaryt == 2) {
                    kosztBazowy = 20;
                }

                double kosztWagi = waga * 2;
                double kosztDostawy = rbEkspres.isSelected() ? 15 : 0;
                double kosztUbezpieczenia = chcUbezpieczenie.isSelected() ? 10 : 0;

                double suma = kosztBazowy + kosztWagi + kosztDostawy + kosztUbezpieczenia;

                lblWynik.setText(String.format("Koszt przesyłki: %.2f zł", suma));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KalkulatorPrzesylki().setVisible(true);
            }
        });
    }
}