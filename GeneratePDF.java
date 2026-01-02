import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.geom.*;

public class GeneratePDF {
    
    public static void main(String[] args) {
        try {
            // Create HTML content
            String htmlContent = generateHTMLContent();
            
            // Save as HTML file
            FileWriter htmlWriter = new FileWriter("LeetCode_961_Documentation.html");
            htmlWriter.write(htmlContent);
            htmlWriter.close();
            
            System.out.println("✓ HTML documentation created: LeetCode_961_Documentation.html");
            System.out.println("✓ Open this file in a browser and use Print to PDF to create a PDF");
            System.out.println("\nInstructions to create PDF:");
            System.out.println("1. Open LeetCode_961_Documentation.html in your browser");
            System.out.println("2. Press Ctrl+P (or Cmd+P on Mac)");
            System.out.println("3. Select 'Save as PDF' as destination");
            System.out.println("4. Click Save");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String generateHTMLContent() {
        return "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>LeetCode 961 - N-Repeated Element in Size 2N Array</title>\n" +
            "    <style>\n" +
            "        @media print {\n" +
            "            body { margin: 0; }\n" +
            "            .page-break { page-break-before: always; }\n" +
            "        }\n" +
            "        body {\n" +
            "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
            "            line-height: 1.6;\n" +
            "            color: #333;\n" +
            "            max-width: 900px;\n" +
            "            margin: 0 auto;\n" +
            "            padding: 20px;\n" +
            "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
            "        }\n" +
            "        .container {\n" +
            "            background: white;\n" +
            "            padding: 40px;\n" +
            "            border-radius: 10px;\n" +
            "            box-shadow: 0 10px 30px rgba(0,0,0,0.3);\n" +
            "        }\n" +
            "        h1 {\n" +
            "            color: #667eea;\n" +
            "            border-bottom: 3px solid #667eea;\n" +
            "            padding-bottom: 10px;\n" +
            "            font-size: 2.5em;\n" +
            "            margin-top: 0;\n" +
            "        }\n" +
            "        h2 {\n" +
            "            color: #764ba2;\n" +
            "            margin-top: 30px;\n" +
            "            font-size: 1.8em;\n" +
            "            border-left: 5px solid #764ba2;\n" +
            "            padding-left: 15px;\n" +
            "        }\n" +
            "        h3 {\n" +
            "            color: #555;\n" +
            "            font-size: 1.3em;\n" +
            "            margin-top: 20px;\n" +
            "        }\n" +
            "        .problem-box {\n" +
            "            background: #f8f9fa;\n" +
            "            border-left: 5px solid #667eea;\n" +
            "            padding: 20px;\n" +
            "            margin: 20px 0;\n" +
            "            border-radius: 5px;\n" +
            "        }\n" +
            "        .code-block {\n" +
            "            background: #2d2d2d;\n" +
            "            color: #f8f8f2;\n" +
            "            padding: 20px;\n" +
            "            border-radius: 8px;\n" +
            "            overflow-x: auto;\n" +
            "            margin: 15px 0;\n" +
            "            font-family: 'Consolas', 'Monaco', monospace;\n" +
            "            font-size: 14px;\n" +
            "            line-height: 1.5;\n" +
            "        }\n" +
            "        .keyword { color: #ff79c6; }\n" +
            "        .type { color: #8be9fd; }\n" +
            "        .function { color: #50fa7b; }\n" +
            "        .comment { color: #6272a4; font-style: italic; }\n" +
            "        .example {\n" +
            "            background: #e7f3ff;\n" +
            "            border-left: 5px solid #2196F3;\n" +
            "            padding: 15px;\n" +
            "            margin: 15px 0;\n" +
            "            border-radius: 5px;\n" +
            "        }\n" +
            "        .step {\n" +
            "            background: #fff9e6;\n" +
            "            border-left: 5px solid #ffc107;\n" +
            "            padding: 15px;\n" +
            "            margin: 10px 0;\n" +
            "            border-radius: 5px;\n" +
            "        }\n" +
            "        .complexity {\n" +
            "            background: #e8f5e9;\n" +
            "            border-left: 5px solid #4caf50;\n" +
            "            padding: 15px;\n" +
            "            margin: 15px 0;\n" +
            "            border-radius: 5px;\n" +
            "        }\n" +
            "        table {\n" +
            "            width: 100%;\n" +
            "            border-collapse: collapse;\n" +
            "            margin: 20px 0;\n" +
            "        }\n" +
            "        th, td {\n" +
            "            border: 1px solid #ddd;\n" +
            "            padding: 12px;\n" +
            "            text-align: left;\n" +
            "        }\n" +
            "        th {\n" +
            "            background: #667eea;\n" +
            "            color: white;\n" +
            "            font-weight: bold;\n" +
            "        }\n" +
            "        tr:nth-child(even) {\n" +
            "            background: #f9f9f9;\n" +
            "        }\n" +
            "        .highlight {\n" +
            "            background: #ffeb3b;\n" +
            "            padding: 2px 5px;\n" +
            "            border-radius: 3px;\n" +
            "            font-weight: bold;\n" +
            "        }\n" +
            "        .footer {\n" +
            "            margin-top: 40px;\n" +
            "            padding-top: 20px;\n" +
            "            border-top: 2px solid #ddd;\n" +
            "            text-align: center;\n" +
            "            color: #777;\n" +
            "        }\n" +
            "        .badge {\n" +
            "            display: inline-block;\n" +
            "            padding: 5px 10px;\n" +
            "            border-radius: 15px;\n" +
            "            font-size: 0.85em;\n" +
            "            font-weight: bold;\n" +
            "            margin: 5px;\n" +
            "        }\n" +
            "        .easy { background: #d4edda; color: #155724; }\n" +
            "        .medium { background: #fff3cd; color: #856404; }\n" +
            "        .hard { background: #f8d7da; color: #721c24; }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\">\n" +
            "        <h1>🎯 LeetCode Problem 961</h1>\n" +
            "        <h2>N-Repeated Element in Size 2N Array</h2>\n" +
            "        \n" +
            "        <div style=\"margin: 20px 0;\">\n" +
            "            <span class=\"badge easy\">Easy</span>\n" +
            "            <span class=\"badge\" style=\"background: #e3f2fd; color: #1565c0;\">Array</span>\n" +
            "            <span class=\"badge\" style=\"background: #f3e5f5; color: #6a1b9a;\">Hash Table</span>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"problem-box\">\n" +
            "            <h3>📋 Problem Statement</h3>\n" +
            "            <p>You are given an integer array <code>nums</code> with the following properties:</p>\n" +
            "            <ul>\n" +
            "                <li><code>nums.length == 2 * n</code></li>\n" +
            "                <li><code>nums</code> contains <code>n + 1</code> unique elements</li>\n" +
            "                <li>Exactly one element of <code>nums</code> is repeated <code>n</code> times</li>\n" +
            "            </ul>\n" +
            "            <p><strong>Task:</strong> Return the element that is repeated <code>n</code> times.</p>\n" +
            "        </div>\n" +
            "\n" +
            "        <h2>📝 Examples</h2>\n" +
            "        \n" +
            "        <div class=\"example\">\n" +
            "            <h3>Example 1</h3>\n" +
            "            <p><strong>Input:</strong> nums = [1, 2, 3, 3]</p>\n" +
            "            <p><strong>Output:</strong> 3</p>\n" +
            "            <p><strong>Explanation:</strong> Array length is 4 (2*n where n=2). Element 3 appears 2 times.</p>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"example\">\n" +
            "            <h3>Example 2</h3>\n" +
            "            <p><strong>Input:</strong> nums = [2, 1, 2, 5, 3, 2]</p>\n" +
            "            <p><strong>Output:</strong> 2</p>\n" +
            "            <p><strong>Explanation:</strong> Array length is 6 (2*n where n=3). Element 2 appears 3 times.</p>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"example\">\n" +
            "            <h3>Example 3</h3>\n" +
            "            <p><strong>Input:</strong> nums = [5, 1, 5, 2, 5, 3, 5, 4]</p>\n" +
            "            <p><strong>Output:</strong> 5</p>\n" +
            "            <p><strong>Explanation:</strong> Array length is 8 (2*n where n=4). Element 5 appears 4 times.</p>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"problem-box\">\n" +
            "            <h3>⚠️ Constraints</h3>\n" +
            "            <ul>\n" +
            "                <li><code>2 ≤ n ≤ 5000</code></li>\n" +
            "                <li><code>nums.length == 2 * n</code></li>\n" +
            "                <li><code>0 ≤ nums[i] ≤ 10<sup>4</sup></code></li>\n" +
            "                <li><code>nums</code> contains <code>n + 1</code> unique elements</li>\n" +
            "                <li>One element is repeated exactly <code>n</code> times</li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"page-break\"></div>\n" +
            "\n" +
            "        <h2>💡 Solution Approach</h2>\n" +
            "        \n" +
            "        <h3>HashSet Approach (Optimal)</h3>\n" +
            "        <p>The most efficient solution uses a <span class=\"highlight\">HashSet</span> to track elements we've already seen:</p>\n" +
            "        \n" +
            "        <div class=\"code-block\">\n" +
            "<span class=\"keyword\">class</span> <span class=\"type\">Solution</span> {\n" +
            "    <span class=\"keyword\">public</span> <span class=\"type\">int</span> <span class=\"function\">repeatedNTimes</span>(<span class=\"type\">int</span>[] nums) {\n" +
            "        <span class=\"type\">HashSet</span>&lt;<span class=\"type\">Integer</span>&gt; set = <span class=\"keyword\">new</span> <span class=\"type\">HashSet</span>&lt;&gt;();\n" +
            "        \n" +
            "        <span class=\"keyword\">for</span>(<span class=\"type\">int</span> num : nums) {\n" +
            "            <span class=\"keyword\">if</span>(set.<span class=\"function\">contains</span>(num)) {\n" +
            "                <span class=\"keyword\">return</span> num;  <span class=\"comment\">// Found the repeated element!</span>\n" +
            "            }\n" +
            "            set.<span class=\"function\">add</span>(num);  <span class=\"comment\">// Mark this element as seen</span>\n" +
            "        }\n" +
            "        <span class=\"keyword\">return</span> -1;  <span class=\"comment\">// This will never execute given constraints</span>\n" +
            "    }\n" +
            "}\n" +
            "        </div>\n" +
            "\n" +
            "        <h2>🔍 Algorithm Walkthrough</h2>\n" +
            "        <p>Let's trace through <strong>Example 2</strong>: nums = [2, 1, 2, 5, 3, 2]</p>\n" +
            "\n" +
            "        <table>\n" +
            "            <tr>\n" +
            "                <th>Step</th>\n" +
            "                <th>Current Element</th>\n" +
            "                <th>HashSet State</th>\n" +
            "                <th>Action</th>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td>1</td>\n" +
            "                <td>2</td>\n" +
            "                <td>{ }</td>\n" +
            "                <td>2 not in set → Add 2</td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td>2</td>\n" +
            "                <td>1</td>\n" +
            "                <td>{2}</td>\n" +
            "                <td>1 not in set → Add 1</td>\n" +
            "            </tr>\n" +
            "            <tr style=\"background: #c8e6c9;\">\n" +
            "                <td>3</td>\n" +
            "                <td>2</td>\n" +
            "                <td>{2, 1}</td>\n" +
            "                <td><strong>2 IS in set → Return 2 ✓</strong></td>\n" +
            "            </tr>\n" +
            "        </table>\n" +
            "\n" +
            "        <div class=\"step\">\n" +
            "            <h3>Step-by-Step Execution</h3>\n" +
            "            <ol>\n" +
            "                <li><strong>Initialize:</strong> Create an empty HashSet</li>\n" +
            "                <li><strong>Iterate:</strong> Loop through each element in the array</li>\n" +
            "                <li><strong>Check:</strong> If element exists in HashSet → Found repeated element!</li>\n" +
            "                <li><strong>Add:</strong> If not found, add element to HashSet</li>\n" +
            "                <li><strong>Return:</strong> Return the repeated element when found</li>\n" +
            "            </ol>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"page-break\"></div>\n" +
            "\n" +
            "        <h2>⚡ Complexity Analysis</h2>\n" +
            "        \n" +
            "        <div class=\"complexity\">\n" +
            "            <h3>Time Complexity: O(n)</h3>\n" +
            "            <ul>\n" +
            "                <li>We iterate through the array at most once</li>\n" +
            "                <li>HashSet operations (add, contains) are O(1) on average</li>\n" +
            "                <li>In the worst case, we check n+1 elements before finding the duplicate</li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"complexity\">\n" +
            "            <h3>Space Complexity: O(n)</h3>\n" +
            "            <ul>\n" +
            "                <li>HashSet stores at most n+1 unique elements</li>\n" +
            "                <li>In practice, usually stores fewer elements (stops when duplicate found)</li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "\n" +
            "        <h2>🎨 Visualization Features</h2>\n" +
            "        <p>Our Java Swing application provides an interactive visualization with:</p>\n" +
            "        \n" +
            "        <div class=\"problem-box\">\n" +
            "            <h3>✨ Key Features</h3>\n" +
            "            <ul>\n" +
            "                <li>🎯 <strong>Array Visualization:</strong> See each element highlighted as it's processed</li>\n" +
            "                <li>📦 <strong>HashSet Display:</strong> Watch elements being added to the set in real-time</li>\n" +
            "                <li>📝 <strong>Code Highlighting:</strong> Follow along with line-by-line execution</li>\n" +
            "                <li>📖 <strong>Step Explanations:</strong> Detailed explanation for each operation</li>\n" +
            "                <li>▶️ <strong>Auto-Play Mode:</strong> Automatic step-through animation</li>\n" +
            "                <li>⏯️ <strong>Manual Control:</strong> Step through at your own pace</li>\n" +
            "                <li>⚙️ <strong>Speed Control:</strong> Adjust animation speed from fast to slow</li>\n" +
            "                <li>🎨 <strong>Vibrant UI:</strong> Color-coded buttons and visual feedback</li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "\n" +
            "        <h2>🔑 Key Insights</h2>\n" +
            "        <div class=\"step\">\n" +
            "            <ul>\n" +
            "                <li>The HashSet approach is optimal because we can stop as soon as we find a duplicate</li>\n" +
            "                <li>Since one element appears n times and there are n+1 unique elements in an array of size 2n, we're guaranteed to find the duplicate quickly</li>\n" +
            "                <li>No need to count frequencies - just detect first occurrence of a duplicate</li>\n" +
            "                <li>Alternative approaches exist (sorting, frequency map) but HashSet is most elegant</li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "\n" +
            "        <h2>🚀 Running the Visualization</h2>\n" +
            "        <div class=\"code-block\">\n" +
            "<span class=\"comment\">// Compile the Java file</span>\n" +
            "javac \"Leetcode_problem _number _961.java\"\n" +
            "\n" +
            "<span class=\"comment\">// Run the visualization</span>\n" +
            "java Leetcode_problem_number_961\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"footer\">\n" +
            "            <p><strong>GitHub Repository:</strong> DSA_Playlist_by_Animation</p>\n" +
            "            <p><strong>Author:</strong> Sannikumar85</p>\n" +
            "            <p><strong>Problem:</strong> LeetCode #961 - N-Repeated Element in Size 2N Array</p>\n" +
            "            <p><strong>Date:</strong> January 2, 2026</p>\n" +
            "            <hr style=\"margin: 20px 0;\">\n" +
            "            <p style=\"font-size: 0.9em;\">This document was auto-generated for educational purposes.<br>\n" +
            "            Interactive visualization available in the repository.</p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
    }
}
