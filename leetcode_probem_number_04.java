import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class leetcode_probem_number_04 extends JFrame {
    private int[] nums1;
    private int[] nums2;
    private JPanel visualPanel;
    private JTextArea stepArea;
    private JButton startBtn, resetBtn, nextStepBtn;
    private List<AnimationStep> steps;
    private int currentStep = 0;
    private double median = 0;
    
    // Animation step class to store state
    class AnimationStep {
        String description;
        int left1, right1, mid1;
        int left2, right2, mid2;
        int partition1, partition2;
        boolean isFinalStep;
        
        AnimationStep(String desc, int l1, int r1, int m1, int p1, int p2) {
            this.description = desc;
            this.left1 = l1;
            this.right1 = r1;
            this.mid1 = m1;
            this.partition1 = p1;
            this.partition2 = p2;
            this.isFinalStep = false;
        }
    }
    
    public leetcode_probem_number_04() {
        setTitle("Median of Two Sorted Arrays - Step by Step Visualization");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Arrays - Enter Your Own Values"));
        
        // Instructions
        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel instruction = new JLabel("Enter arrays in format [1,2,3] or modify examples below:");
        instruction.setFont(new Font("Arial", Font.BOLD, 12));
        instructionPanel.add(instruction);
        
        // Example selector
        JPanel examplePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel exLabel = new JLabel("Load Example:");
        String[] examples = {
            "Custom",
            "Example 1: [1,3] & [2] → Median: 2.0",
            "Example 2: [1,2] & [3,4] → Median: 2.5",
            "Example 3: [1,2,3,4,5] & [6,7,8,9,10]",
            "Example 4: [] & [1]",
            "Example 5: [2] & [1,3,4]",
            "Example 6: [1] & [2,3,4,5,6]"
        };
        JComboBox<String> exampleBox = new JComboBox<>(examples);
        examplePanel.add(exLabel);
        examplePanel.add(exampleBox);
        
        // Input fields panel
        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel label1 = new JLabel("Array 1:");
        JTextField array1Field = new JTextField("[1,3]", 15);
        array1Field.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JLabel label2 = new JLabel("Array 2:");
        JTextField array2Field = new JTextField("[2]", 15);
        array2Field.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        startBtn = new JButton("Start Animation");
        startBtn.setBackground(new Color(100, 200, 100));
        JButton solveBtn = new JButton("Quick Solve");
        solveBtn.setBackground(new Color(100, 150, 250));
        resetBtn = new JButton("Reset");
        nextStepBtn = new JButton("Next Step");
        nextStepBtn.setEnabled(false);
        nextStepBtn.setBackground(new Color(255, 200, 100));
        
        fieldsPanel.add(label1);
        fieldsPanel.add(array1Field);
        fieldsPanel.add(label2);
        fieldsPanel.add(array2Field);
        
        buttonPanel.add(startBtn);
        buttonPanel.add(solveBtn);
        buttonPanel.add(nextStepBtn);
        buttonPanel.add(resetBtn);
        
        inputPanel.add(instructionPanel);
        inputPanel.add(examplePanel);
        inputPanel.add(fieldsPanel);
        inputPanel.add(buttonPanel);
        
        // Visualization Panel
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawVisualization((Graphics2D) g);
            }
        };
        visualPanel.setPreferredSize(new Dimension(1200, 400));
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setBorder(BorderFactory.createTitledBorder("Algorithm Visualization"));
        
        // Step description area
        stepArea = new JTextArea(12, 50);
        stepArea.setEditable(false);
        stepArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        stepArea.setBorder(BorderFactory.createTitledBorder("Step-by-Step Explanation"));
        JScrollPane scrollPane = new JScrollPane(stepArea);
        
        // Result Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        JLabel resultLabel = new JLabel("Median: ");
        JTextField resultField = new JTextField(20);
        resultField.setEditable(false);
        resultField.setFont(new Font("Arial", Font.BOLD, 16));
        resultPanel.add(resultLabel);
        resultPanel.add(resultField);
        
        // Event Handlers
        exampleBox.addActionListener(e -> {
            String selected = (String) exampleBox.getSelectedItem();
            if (selected.startsWith("Example 1")) {
                array1Field.setText("[1,3]");
                array2Field.setText("[2]");
            } else if (selected.startsWith("Example 2")) {
                array1Field.setText("[1,2]");
                array2Field.setText("[3,4]");
            } else if (selected.startsWith("Example 3")) {
                array1Field.setText("[1,2,3,4,5]");
                array2Field.setText("[6,7,8,9,10]");
            } else if (selected.startsWith("Example 4")) {
                array1Field.setText("[]");
                array2Field.setText("[1]");
            } else if (selected.startsWith("Example 5")) {
                array1Field.setText("[2]");
                array2Field.setText("[1,3,4]");
            } else if (selected.startsWith("Example 6")) {
                array1Field.setText("[1]");
                array2Field.setText("[2,3,4,5,6]");
            }
        });
        
        startBtn.addActionListener(e -> {
            try {
                nums1 = parseArray(array1Field.getText());
                nums2 = parseArray(array2Field.getText());
                initializeAnimation();
                currentStep = 0;
                nextStepBtn.setEnabled(true);
                startBtn.setEnabled(false);
                solveBtn.setEnabled(false);
                showCurrentStep();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input format. Use [1,2,3]\n" +
                    "Examples: [1,3] or [1,2,3,4] or []", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        solveBtn.addActionListener(e -> {
            try {
                int[] arr1 = parseArray(array1Field.getText());
                int[] arr2 = parseArray(array2Field.getText());
                
                // Direct solve without animation
                double result = findMedianDirectly(arr1, arr2);
                
                String mergedStr = getMergedArrayString(arr1, arr2);
                
                JOptionPane.showMessageDialog(this,
                    "Input Array 1: " + arrayToString(arr1) + "\n" +
                    "Input Array 2: " + arrayToString(arr2) + "\n\n" +
                    "Merged (sorted): " + mergedStr + "\n\n" +
                    "MEDIAN = " + String.format("%.5f", result) + "\n\n" +
                    "Time Complexity: O(log(min(m,n)))\n" +
                    "Space Complexity: O(1)",
                    "Solution Result",
                    JOptionPane.INFORMATION_MESSAGE);
                    
                resultField.setText(String.format("%.5f", result));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input format. Use [1,2,3]\n" +
                    "Examples: [1,3] or [1,2,3,4] or []", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        nextStepBtn.addActionListener(e -> {
            if (currentStep < steps.size() - 1) {
                currentStep++;
                showCurrentStep();
            }
            if (currentStep >= steps.size() - 1) {
                nextStepBtn.setEnabled(false);
                resultField.setText(String.format("%.5f", median));
            }
        });
        
        resetBtn.addActionListener(e -> {
            currentStep = 0;
            steps = null;
            median = 0;
            stepArea.setText("");
            resultField.setText("");
            visualPanel.repaint();
            nextStepBtn.setEnabled(false);
            startBtn.setEnabled(true);
            solveBtn.setEnabled(true);
        });
        
        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(visualPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(resultPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    private int[] parseArray(String input) {
        input = input.trim().replace("[", "").replace("]", "");
        if (input.isEmpty()) return new int[0];
        String[] parts = input.split(",");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }
        return arr;
    }
    
    private void initializeAnimation() {
        steps = new ArrayList<>();
        
        // Initial step
        steps.add(new AnimationStep(
            "STEP 0: Initial Setup\n" +
            "Array 1: " + arrayToString(nums1) + " (length = " + nums1.length + ")\n" +
            "Array 2: " + arrayToString(nums2) + " (length = " + nums2.length + ")\n" +
            "Total elements: " + (nums1.length + nums2.length) + "\n" +
            "We'll use binary search on the smaller array for O(log(min(m,n))) complexity.",
            0, 0, 0, -1, -1
        ));
        
        // Run the algorithm and capture steps
        median = findMedianSortedArrays(nums1, nums2);
    }
    
    private double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = temp;
            nums2 = nums2;
            // Swap references
            temp = this.nums1;
            this.nums1 = this.nums2;
            this.nums2 = temp;
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int left = 0, right = m;
        int halfLen = (m + n + 1) / 2;
        
        int iteration = 1;
        
        while (left <= right) {
            int partition1 = (left + right) / 2;
            int partition2 = halfLen - partition1;
            
            steps.add(new AnimationStep(
                String.format("STEP %d: Binary Search Iteration\n" +
                "Left = %d, Right = %d, Mid (partition1) = %d\n" +
                "partition1 = %d (elements from nums1 on left side)\n" +
                "partition2 = %d (elements from nums2 on left side)\n" +
                "Total left side elements: %d",
                iteration, left, right, partition1, partition1, partition2, halfLen),
                left, right, partition1, partition1, partition2
            ));
            
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            steps.add(new AnimationStep(
                String.format("Checking partition:\n" +
                "maxLeft1 = %s, minRight1 = %s\n" +
                "maxLeft2 = %s, minRight2 = %s\n" +
                "Condition: maxLeft1 <= minRight2 AND maxLeft2 <= minRight1",
                formatValue(maxLeft1), formatValue(minRight1),
                formatValue(maxLeft2), formatValue(minRight2)),
                left, right, partition1, partition1, partition2
            ));
            
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Found correct partition
                double result;
                if ((m + n) % 2 == 0) {
                    result = (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                    steps.add(new AnimationStep(
                        "✓ FOUND CORRECT PARTITION!\n" +
                        "Total length is EVEN: " + (m + n) + "\n" +
                        "Median = (max(maxLeft1, maxLeft2) + min(minRight1, minRight2)) / 2\n" +
                        "Median = (" + Math.max(maxLeft1, maxLeft2) + " + " + 
                        Math.min(minRight1, minRight2) + ") / 2 = " + result,
                        left, right, partition1, partition1, partition2
                    ));
                } else {
                    result = Math.max(maxLeft1, maxLeft2);
                    steps.add(new AnimationStep(
                        "✓ FOUND CORRECT PARTITION!\n" +
                        "Total length is ODD: " + (m + n) + "\n" +
                        "Median = max(maxLeft1, maxLeft2)\n" +
                        "Median = " + result,
                        left, right, partition1, partition1, partition2
                    ));
                }
                steps.get(steps.size() - 1).isFinalStep = true;
                return result;
            } else if (maxLeft1 > minRight2) {
                // Too many elements from nums1, move left
                steps.add(new AnimationStep(
                    "maxLeft1 (" + maxLeft1 + ") > minRight2 (" + minRight2 + ")\n" +
                    "Too many elements from nums1 on the left.\n" +
                    "Move partition1 LEFT: right = partition1 - 1 = " + (partition1 - 1),
                    left, partition1 - 1, partition1, partition1, partition2
                ));
                right = partition1 - 1;
            } else {
                // Too few elements from nums1, move right
                steps.add(new AnimationStep(
                    "maxLeft2 (" + maxLeft2 + ") > minRight1 (" + minRight1 + ")\n" +
                    "Too few elements from nums1 on the left.\n" +
                    "Move partition1 RIGHT: left = partition1 + 1 = " + (partition1 + 1),
                    partition1 + 1, right, partition1, partition1, partition2
                ));
                left = partition1 + 1;
            }
            
            iteration++;
        }
        
        return 0.0;
    }
    
    private String formatValue(int val) {
        if (val == Integer.MIN_VALUE) return "-∞";
        if (val == Integer.MAX_VALUE) return "+∞";
        return String.valueOf(val);
    }
    
    private String arrayToString(int[] arr) {
        if (arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    private double findMedianDirectly(int[] nums1, int[] nums2) {
        // Direct calculation without animation
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int left = 0, right = m;
        int halfLen = (m + n + 1) / 2;
        
        while (left <= right) {
            int partition1 = (left + right) / 2;
            int partition2 = halfLen - partition1;
            
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                if ((m + n) % 2 == 0) {
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                } else {
                    return Math.max(maxLeft1, maxLeft2);
                }
            } else if (maxLeft1 > minRight2) {
                right = partition1 - 1;
            } else {
                left = partition1 + 1;
            }
        }
        
        return 0.0;
    }
    
    private String getMergedArrayString(int[] arr1, int[] arr2) {
        int[] merged = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        
        while (i < arr1.length) merged[k++] = arr1[i++];
        while (j < arr2.length) merged[k++] = arr2[j++];
        
        return arrayToString(merged);
    }
    
    private void showCurrentStep() {
        if (steps == null || currentStep >= steps.size()) return;
        
        AnimationStep step = steps.get(currentStep);
        stepArea.setText(step.description);
        visualPanel.repaint();
    }
    
    private void drawVisualization(Graphics2D g2d) {
        if (nums1 == null || nums2 == null || steps == null || currentStep >= steps.size()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("Enter arrays and click 'Start Animation' to begin", 50, 200);
            return;
        }
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        AnimationStep step = steps.get(currentStep);
        
        int startX = 50;
        int startY = 80;
        int cellSize = 60;
        int gap = 10;
        
        // Draw Array 1
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Array 1:", startX, startY - 20);
        
        for (int i = 0; i < nums1.length; i++) {
            int x = startX + i * (cellSize + gap);
            
            // Highlight partition
            if (step.partition1 != -1 && i == step.partition1) {
                g2d.setColor(new Color(255, 200, 0, 100));
                g2d.fillRect(x - 5, startY - 5, 5, cellSize + 10);
            }
            
            // Highlight based on position relative to partition
            if (step.partition1 != -1) {
                if (i < step.partition1) {
                    g2d.setColor(new Color(100, 200, 100)); // Left side - green
                } else {
                    g2d.setColor(new Color(200, 100, 100)); // Right side - red
                }
            } else {
                g2d.setColor(new Color(200, 200, 255));
            }
            
            g2d.fillRect(x, startY, cellSize, cellSize);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, startY, cellSize, cellSize);
            
            // Draw value
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            String val = String.valueOf(nums1[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(val);
            int textHeight = fm.getAscent();
            g2d.drawString(val, x + (cellSize - textWidth) / 2, startY + (cellSize + textHeight) / 2);
            
            // Draw index
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("i=" + i, x + cellSize / 2 - 10, startY + cellSize + 20);
        }
        
        // Draw partition line for Array 1
        if (step.partition1 >= 0 && step.partition1 <= nums1.length) {
            g2d.setColor(new Color(255, 150, 0));
            g2d.setStroke(new BasicStroke(3));
            int lineX = startX + step.partition1 * (cellSize + gap) - 5;
            g2d.drawLine(lineX, startY - 10, lineX, startY + cellSize + 10);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("P1=" + step.partition1, lineX - 15, startY - 15);
        }
        
        // Draw Array 2
        startY = 220;
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Array 2:", startX, startY - 20);
        
        for (int i = 0; i < nums2.length; i++) {
            int x = startX + i * (cellSize + gap);
            
            // Highlight partition
            if (step.partition2 != -1 && i == step.partition2) {
                g2d.setColor(new Color(255, 200, 0, 100));
                g2d.fillRect(x - 5, startY - 5, 5, cellSize + 10);
            }
            
            // Highlight based on position relative to partition
            if (step.partition2 != -1) {
                if (i < step.partition2) {
                    g2d.setColor(new Color(100, 200, 100)); // Left side - green
                } else {
                    g2d.setColor(new Color(200, 100, 100)); // Right side - red
                }
            } else {
                g2d.setColor(new Color(255, 200, 200));
            }
            
            g2d.fillRect(x, startY, cellSize, cellSize);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, startY, cellSize, cellSize);
            
            // Draw value
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            String val = String.valueOf(nums2[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(val);
            int textHeight = fm.getAscent();
            g2d.drawString(val, x + (cellSize - textWidth) / 2, startY + (cellSize + textHeight) / 2);
            
            // Draw index
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("i=" + i, x + cellSize / 2 - 10, startY + cellSize + 20);
        }
        
        // Draw partition line for Array 2
        if (step.partition2 >= 0 && step.partition2 <= nums2.length) {
            g2d.setColor(new Color(255, 150, 0));
            g2d.setStroke(new BasicStroke(3));
            int lineX = startX + step.partition2 * (cellSize + gap) - 5;
            g2d.drawLine(lineX, startY - 10, lineX, startY + cellSize + 10);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("P2=" + step.partition2, lineX - 15, startY - 15);
        }
        
        // Draw legend
        int legendY = 350;
        g2d.setFont(new Font("Arial", Font.PLAIN, 13));
        g2d.setColor(new Color(100, 200, 100));
        g2d.fillRect(startX, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX, legendY, 20, 20);
        g2d.drawString("Left partition (smaller elements)", startX + 30, legendY + 15);
        
        g2d.setColor(new Color(200, 100, 100));
        g2d.fillRect(startX + 250, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX + 250, legendY, 20, 20);
        g2d.drawString("Right partition (larger elements)", startX + 280, legendY + 15);
        
        g2d.setColor(new Color(255, 150, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(startX + 500, legendY + 10, startX + 520, legendY + 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Partition line", startX + 530, legendY + 15);
        
        // Show current search bounds
        if (step.left1 != step.right1 && !step.isFinalStep) {
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("Binary Search Range: [" + step.left1 + ", " + step.right1 + "]", 
                startX + 700, 100);
        }
        
        if (step.isFinalStep) {
            g2d.setColor(new Color(0, 150, 0));
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("✓ SOLUTION FOUND!", startX + 700, 100);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("Median = " + String.format("%.5f", median), startX + 700, 140);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            leetcode_probem_number_04 app = new leetcode_probem_number_04();
            app.setVisible(true);
        });
    }
}
