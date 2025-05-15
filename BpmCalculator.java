import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BpmCalculator extends JFrame {
    private JTextField nField, bpmField;
    private JTextArea resultArea;

    public BpmCalculator() {
        setTitle("Adofai圆形轨道BPM与角度生成器");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nLabel = new JLabel("请输入轨道总数n:");
        nField = new JTextField();
        panel.add(nLabel);
        panel.add(nField);

        JLabel bpmLabel = new JLabel("请输入直线BPM:");
        bpmField = new JTextField();
        panel.add(bpmLabel);
        panel.add(bpmField);

        JButton calculateButton = new JButton("计算");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });
        panel.add(calculateButton);

        add(panel, BorderLayout.NORTH);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void calculate() {
        try {
            int n = Integer.parseInt(nField.getText());
            double straightBpm = Double.parseDouble(bpmField.getText());
            if (n <= 0 || straightBpm <= 0) {
                throw new IllegalArgumentException("轨道总数和直线BPM必须大于0");
            }

            double angleStep = 360.0 / n;
            double startAngle = angleStep / 2;

            double innerBpm, outerBpm;
            if (startAngle < 180) {
                innerBpm = straightBpm * (1 - startAngle / 180);
                outerBpm = straightBpm + (straightBpm - innerBpm);
            } else {
                outerBpm = straightBpm * ((startAngle / 180) - 1);
                innerBpm = straightBpm - (outerBpm - straightBpm);
            }

            double innerAngle = 180 - startAngle;
            double outerAngle = 180 + startAngle;

            resultArea.setText(String.format(
                    "轨道BPM计算结果：\n" +
                            "轨道编号: %d\n" +
                            "轨道角度: %.2f°\n" +
                            "内圈BPM: %.1f\n" +
                            "外圈BPM: %.1f\n" +
                            "内圈角度: %.2f°\n" +
                            "外圈角度: %.2f°",
                    1, startAngle, innerBpm, outerBpm, innerAngle, outerAngle));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "输入错误: 数字格式不正确\n请确保输入是有效的数字。", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "输入错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BpmCalculator().setVisible(true);
            }
        });
    }
}