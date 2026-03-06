/*
 * ComponentGallery.java
 *
 * A small “kitchen sink” window that instantiates one of each component
 * (standard + custom styles) to visually validate your FlatLaf theme.
 *
 * Requirements:
 * - flatlaf (core)
 * - flatlaf-extras (for FlatTextField leading/trailing icons; optional)
 *
 * Tip: Put your .properties into src/main/resources/themes/ and call
 * FlatLaf.registerCustomDefaultsSource("themes") before setup().
 */

package demo;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class ComponentGallery {

    public static void main(String[] args) {
        // Register custom FlatLaf defaults from src/main/resources/demo/themes.
        FlatLaf.registerCustomDefaultsSource("demo.themes");
        // Install FlatLightLaf once (skip if already installed by Main).
        if (!(UIManager.getLookAndFeel() instanceof FlatLightLaf)) {
            FlatLightLaf.setup();
        }

        // Optional platform/UI behavior tweaks.
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("flatlaf.useWindowDecorations", "true");

        // Build and show the Swing UI on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> new ComponentGallery().showUI());
    }

    private void showUI() {
        // Top-level application window.
        JFrame frame = new JFrame("FlatLaf Component Gallery");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Main vertical container with section cards.
        JPanel content = new JPanel();
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Section 1: text inputs and related field components.
        content.add(section("Inputs", buildInputs()));
        content.add(Box.createVerticalStrut(16));
        // Section 2: validation states (error/warning outlines).
        content.add(section("Validation / Errors", buildValidation()));
        content.add(Box.createVerticalStrut(16));
        // Section 3: checkbox states.
        content.add(section("CheckBox", buildCheckboxes()));
        content.add(Box.createVerticalStrut(16));
        // Section 4: tab variants.
        content.add(section("Tabs", buildTabs()));
        content.add(Box.createVerticalStrut(16));
        // Section 5: combobox variants.
        content.add(section("Combobox", buildCombobox()));
        content.add(Box.createVerticalStrut(16));
        // Section 6: selectbox variants.
        content.add(section("Selectbox", buildSelectbox()));
        content.add(Box.createVerticalStrut(16));
        // Section 7: dropbox variants.
        content.add(section("Dropbox", buildDropbox()));
        content.add(Box.createVerticalStrut(16));
        // Section 8: button styles/states.
        content.add(section("Buttons", buildButtons()));

        // Scroll container for the full page content.
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        frame.setContentPane(scroll);

        frame.setSize(720, 900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JComponent section(String title, JComponent body) {
        // Wrapper panel for one titled section.
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setOpaque(false);

        // Section header label.
        JLabel h = new JLabel(title);
        h.putClientProperty(FlatClientProperties.STYLE, "font: +2"); // FlatLaf text style bump
        p.add(h, BorderLayout.NORTH);

        // Card-like container with themed border around section content.
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")),
                new EmptyBorder(16, 16, 16, 16)
        ));
        card.add(body, BorderLayout.CENTER);

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    private JComponent buildInputs() {
        // Grid container for input component samples.
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, 12, 16);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        // JTextField: basic single-line input.
        grid.add(labeled("Single-line input", singleLineInput()), gc);

        // FlatTextField: search field with leading/trailing controls.
        gc.gridy++;
        grid.add(labeled("Search input", searchInput()), gc);

        // JFormattedTextField: masked/phone input.
        gc.gridy++;
        grid.add(labeled("Phone input", phoneInput()), gc);

        // JPasswordField: secure text input.
        gc.gridy++;
        grid.add(labeled("Password input", passwordInput()), gc);

        // JSpinner: numeric input with step controls.
        gc.gridy++;
        grid.add(labeled("Num input", numInput()), gc);

        // JTextArea inside JScrollPane: multiline input.
        gc.gridy++;
        grid.add(labeled("Multiline input", multilineInput()), gc);

        return grid;
    }

    private JComponent buildValidation() {
        // Vertical container for fields with validation outlines.
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // JTextField with FlatLaf error outline.
        JTextField error = new JTextField();
        error.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Placeholder");
        error.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);

        // JTextField with FlatLaf warning outline.
        JTextField warning = new JTextField("Filled");
        warning.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_WARNING);

        p.add(labeled("Outline = error", error));
        p.add(Box.createVerticalStrut(10));
        p.add(labeled("Outline = warning", warning));
        return p;
    }

    private JComponent buildCheckboxes() {
        // Grid container (one column) for checkbox variants.
        JPanel p = new JPanel(new GridLayout(0, 1, 0, 8));
        p.setOpaque(false);

        // Standard unchecked checkbox.
        JCheckBox def = new JCheckBox("Default");
        // Standard checked checkbox.
        JCheckBox checked = new JCheckBox("Checked", true);

        // Indeterminate (mixed) checkbox.
        JCheckBox mixed = new JCheckBox("Mixed");
        mixed.putClientProperty(FlatClientProperties.SELECTED_STATE, FlatClientProperties.SELECTED_STATE_INDETERMINATE);

        // Disabled unchecked checkbox.
        JCheckBox disabled = new JCheckBox("Disabled");
        disabled.setEnabled(false);

        // Disabled checked checkbox.
        JCheckBox disabledChecked = new JCheckBox("Disabled checked", true);
        disabledChecked.setEnabled(false);

        // Disabled indeterminate checkbox.
        JCheckBox disabledMixed = new JCheckBox("Disabled mixed");
        disabledMixed.setEnabled(false);
        disabledMixed.putClientProperty(FlatClientProperties.SELECTED_STATE, FlatClientProperties.SELECTED_STATE_INDETERMINATE);

        p.add(def);
        p.add(checked);
        p.add(mixed);
        p.add(disabled);
        p.add(disabledChecked);
        p.add(disabledMixed);

        return p;
    }

    private JComponent buildButtons() {
        // Grid container for button styles and states.
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, 12, 12);
        gc.anchor = GridBagConstraints.WEST;

        // Primary action button (STYLE_CLASS = primary).
        Icon diskIcon = new FlatSVGIcon("demo/icons/disk_blue.svg", 16, 16);
        Icon chevronDownIcon = new FlatSVGIcon("demo/icons/shevronDown.svg", 16, 16);

        JButton primary = new JButton("Button", diskIcon);
        primary.putClientProperty(FlatClientProperties.STYLE_CLASS, "primary");

        // Secondary action button.
        JButton secondary = new JButton("Button", diskIcon);
        secondary.putClientProperty(FlatClientProperties.STYLE_CLASS, "secondary");

        // Ghost button (minimal/outlined style).
        JButton ghost = new JButton("Button", diskIcon);
        ghost.putClientProperty(FlatClientProperties.STYLE_CLASS, "ghost");

        // Disabled primary button state.
        JButton disabled = new JButton("Button", diskIcon);
        disabled.setEnabled(false);
        disabled.putClientProperty(FlatClientProperties.STYLE_CLASS, "primary");

        // Icon-only button with SVG icon.
        JButton iconOnly = new JButton(new FlatSVGIcon("demo/icons/disk_blue.svg", 16, 16));
        iconOnly.putClientProperty(FlatClientProperties.STYLE_CLASS, "primary iconOnly");
        iconOnly.setToolTipText("Icon button");

        // Select-like button (label + down arrow icon).
        JButton split = new JButton("Button", chevronDownIcon);
        split.setHorizontalTextPosition(SwingConstants.LEFT);
        split.putClientProperty(FlatClientProperties.STYLE_CLASS, "ghost");

        p.add(primary, gc);
        gc.gridx++;
        p.add(secondary, gc);
        gc.gridx++;
        p.add(ghost, gc);
        gc.gridx++;
        p.add(disabled, gc);

        gc.gridx = 0;
        gc.gridy++;
        p.add(iconOnly, gc);
        gc.gridx++;
        p.add(split, gc);

        return p;
    }

    private JComponent buildDropbox() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel l = new JLabel("Label");
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(4));

        String placeholder = "Select an option";
        String[] items = {placeholder, "Option one", "Option two", "Option three", "Option four", "Option five"};
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        cb.setPreferredSize(new Dimension(220, 24));
        cb.setMaximumSize(new Dimension(220, 24));
        cb.setMaximumRowCount(8);
        cb.setSelectedIndex(0);
        cb.setRenderer(createDropdownRenderer(placeholder, cb));
        p.add(cb);
        p.add(Box.createVerticalStrut(8));

        JCheckBox disabled = new JCheckBox("Disabled");
        disabled.setOpaque(false);
        disabled.setAlignmentX(Component.LEFT_ALIGNMENT);
        disabled.addActionListener(e -> cb.setEnabled(!disabled.isSelected()));
        p.add(disabled);

        return p;
    }

    private JComponent buildCombobox() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel l = new JLabel("Label");
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(4));

        String placeholder = "Type or select";
        String[] items = {placeholder, "Option one", "Option two", "Option three", "Option four", "Option five"};
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setEditable(true);
        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        cb.setPreferredSize(new Dimension(220, 24));
        cb.setMaximumSize(new Dimension(220, 24));
        cb.setMaximumRowCount(8);
        cb.setSelectedIndex(0);
        JTextField editor = (JTextField) cb.getEditor().getEditorComponent();
        editor.setText(placeholder);
        editor.setForeground(UIManager.getColor("TextField.placeholderForeground"));
        editor.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (placeholder.equals(editor.getText())) {
                    editor.setText("");
                    editor.setForeground(UIManager.getColor("Component.foreground"));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (editor.getText().isBlank()) {
                    editor.setText(placeholder);
                    editor.setForeground(UIManager.getColor("TextField.placeholderForeground"));
                }
            }
        });
        cb.setRenderer(createDropdownRenderer(placeholder, cb));
        p.add(cb);
        p.add(Box.createVerticalStrut(8));

        JCheckBox disabled = new JCheckBox("Disabled");
        disabled.setOpaque(false);
        disabled.setAlignmentX(Component.LEFT_ALIGNMENT);
        disabled.addActionListener(e -> {
            boolean isDisabled = disabled.isSelected();
            cb.setEnabled(!isDisabled);
            editor.setEnabled(!isDisabled);
            editor.setForeground(isDisabled
                    ? UIManager.getColor("Component.disabledForeground")
                    : (placeholder.equals(editor.getText())
                    ? UIManager.getColor("TextField.placeholderForeground")
                    : UIManager.getColor("Component.foreground")));
        });
        p.add(disabled);

        return p;
    }

    private JComponent buildSelectbox() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel l = new JLabel("Label");
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(4));

        Icon chevronDown = new FlatSVGIcon("demo/icons/shevronDown.svg", 16, 16);
        JButton field = new JButton("Select multiple", chevronDown);
        field.setHorizontalAlignment(SwingConstants.LEFT);
        field.setHorizontalTextPosition(SwingConstants.LEFT);
        field.setIconTextGap(4);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setPreferredSize(new Dimension(220, 24));
        field.setMaximumSize(new Dimension(220, 24));
        field.putClientProperty(FlatClientProperties.STYLE_CLASS, "secondary");
        field.putClientProperty(FlatClientProperties.STYLE, "margin: 4,4,4,4");
        p.add(field);
        p.add(Box.createVerticalStrut(4));

        JPopupMenu popup = new JPopupMenu();
        popup.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")));

        JCheckBox[] options = new JCheckBox[]{
                new JCheckBox("Option one"),
                new JCheckBox("Option two"),
                new JCheckBox("Option three"),
                new JCheckBox("Option four"),
                new JCheckBox("Option five")
        };
        for (JCheckBox cb : options) {
            cb.setOpaque(true);
            cb.setBackground(UIManager.getColor("PopupMenu.background"));
            cb.setBorder(new EmptyBorder(4, 4, 4, 4));
            cb.setPreferredSize(new Dimension(220, 24));
            cb.addActionListener(e -> updateSelectboxFieldText(field, options));
            popup.add(cb);
        }

        popup.addSeparator();
        JButton submit = new JButton("Submit");
        submit.setHorizontalAlignment(SwingConstants.CENTER);
        submit.setPreferredSize(new Dimension(220, 24));
        submit.putClientProperty(FlatClientProperties.STYLE_CLASS, "secondary");
        submit.addActionListener(e -> popup.setVisible(false));
        popup.add(submit);

        field.addActionListener(e -> {
            if (popup.isVisible()) {
                popup.setVisible(false);
            } else {
                popup.show(field, 0, field.getHeight() + 2);
            }
        });

        JCheckBox disabled = new JCheckBox("Disabled");
        disabled.setOpaque(false);
        disabled.setAlignmentX(Component.LEFT_ALIGNMENT);
        disabled.addActionListener(e -> {
            boolean isDisabled = disabled.isSelected();
            field.setEnabled(!isDisabled);
            if (popup.isVisible()) {
                popup.setVisible(false);
            }
            for (JCheckBox cb : options) {
                cb.setEnabled(!isDisabled);
            }
            submit.setEnabled(!isDisabled);
        });

        p.add(Box.createVerticalStrut(8));
        p.add(disabled);
        return p;
    }

    private void updateSelectboxFieldText(JButton field, JCheckBox[] options) {
        int selectedCount = 0;
        String firstSelected = null;
        String secondSelected = null;

        for (JCheckBox cb : options) {
            if (!cb.isSelected()) {
                continue;
            }
            selectedCount++;
            if (firstSelected == null) {
                firstSelected = cb.getText();
            } else if (secondSelected == null) {
                secondSelected = cb.getText();
            }
        }

        if (selectedCount == 0) {
            field.setText("Select multiple");
        } else if (selectedCount == 1) {
            field.setText(firstSelected);
        } else if (selectedCount == 2) {
            field.setText(firstSelected + ", " + secondSelected);
        } else {
            field.setText(firstSelected + ", " + secondSelected + " +" + (selectedCount - 2));
        }
    }

    private ListCellRenderer<? super String> createDropdownRenderer(String placeholder, JComboBox<String> owner) {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                boolean placeholderValue = placeholder.equals(value) && owner.getSelectedIndex() == 0;
                c.setForeground(placeholderValue
                        ? UIManager.getColor("TextField.placeholderForeground")
                        : (owner.isEnabled() ? UIManager.getColor("Component.foreground")
                        : UIManager.getColor("Component.disabledForeground")));

                if (index >= 0) {
                    Color divider = UIManager.getColor("Custom.neutral_5");
                    if (divider == null) {
                        divider = UIManager.getColor("Component.disabledBorderColor");
                    }
                    c.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 1, 0, divider),
                            new EmptyBorder(4, 4, 4, 4)
                    ));
                } else {
                    c.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                }
                return c;
            }
        };
    }

    private JComponent buildTabs() {
        JPanel p = new JPanel(new GridLayout(0, 1, 0, 12));
        p.setOpaque(false);

        JTabbedPane basic = new JTabbedPane();
        basic.addTab("Text here", tabContent("Selected"));
        basic.addTab("Text here", tabContent("Enabled"));
        basic.addTab("Text here", tabContent("Enabled"));
        basic.addTab("Text here", tabContent("Disabled"));
        basic.setEnabledAt(3, false);
        basic.setSelectedIndex(0);

        JTabbedPane dot = new JTabbedPane();
        dot.addTab("\u2022 Text here", tabContent("Selected"));
        dot.addTab("\u2022 Text here", tabContent("Enabled"));
        dot.addTab("\u2022 Text here", tabContent("Enabled"));
        dot.addTab("\u2022 Text here", tabContent("Disabled"));
        dot.setEnabledAt(3, false);
        dot.setSelectedIndex(0);

        JTabbedPane search = new JTabbedPane();
        search.addTab("\uD83D\uDD0D  Text here", tabContent("Selected"));
        search.addTab("\uD83D\uDD0D  Text here", tabContent("Enabled"));
        search.addTab("\uD83D\uDD0D  Text here", tabContent("Enabled"));
        search.addTab("\uD83D\uDD0D  Text here", tabContent("Disabled"));
        search.setEnabledAt(3, false);
        search.setSelectedIndex(0);

        JTabbedPane searchClosable = new JTabbedPane();
        searchClosable.addTab("\uD83D\uDD0D  Text here  \u2715", tabContent("Selected"));
        searchClosable.addTab("\uD83D\uDD0D  Text here  \u2715", tabContent("Enabled"));
        searchClosable.addTab("\uD83D\uDD0D  Text here  \u2715", tabContent("Enabled"));
        searchClosable.addTab("\uD83D\uDD0D  Text here  \u2715", tabContent("Disabled"));
        searchClosable.setEnabledAt(3, false);
        searchClosable.setSelectedIndex(0);

        p.add(basic);
        p.add(dot);
        p.add(search);
        p.add(searchClosable);
        return p;
    }

    private JComponent tabContent(String text) {
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setPreferredSize(new Dimension(120, 1));

        JLabel l = new JLabel(text);
        l.setBorder(new EmptyBorder(8, 0, 0, 0));
        l.setForeground(UIManager.getColor("Label.disabledForeground"));
        content.add(l, BorderLayout.WEST);
        return content;
    }

    // ---------- individual components ----------

    private JComponent singleLineInput() {
        // Basic JTextField with placeholder text.
        JTextField tf = new JTextField();
        tf.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Placeholder");
        return tf;
    }

    private JComponent searchInput() {
        // FlatTextField supports built-in leading/trailing accessory components.
        FlatTextField tf = new FlatTextField();
        tf.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Placeholder");

        // Leading icon with base paddings: left 4, icon->text 4.
        JLabel icon = new JLabel(new FlatSVGIcon("demo/icons/find.svg", 16, 16), SwingConstants.CENTER);
        icon.setPreferredSize(new Dimension(16, 16));
        icon.setMinimumSize(new Dimension(16, 16));
        icon.setMaximumSize(new Dimension(16, 16));
        JPanel leading = new JPanel(new BorderLayout());
        leading.setOpaque(false);
        leading.setBorder(new EmptyBorder(0, 4, 0, 4));
        leading.add(icon, BorderLayout.CENTER);
        tf.setLeadingComponent(leading);

        // Trailing controls with base paddings: text->icon 4, icon->icon 4, right 4.
        JButton clear = new JButton("\u2715");
        clear.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        clear.setFocusable(false);
        clear.setMargin(new Insets(0, 0, 0, 0));
        clear.setBorder(BorderFactory.createEmptyBorder());
        clear.setPreferredSize(new Dimension(16, 16));
        clear.setMinimumSize(new Dimension(16, 16));
        clear.setMaximumSize(new Dimension(16, 16));
        clear.addActionListener(e -> tf.setText(""));

        JLabel chevron = new JLabel(new FlatSVGIcon("demo/icons/shevronDown.svg", 16, 16), SwingConstants.CENTER);
        chevron.setPreferredSize(new Dimension(16, 16));
        chevron.setMinimumSize(new Dimension(16, 16));
        chevron.setMaximumSize(new Dimension(16, 16));

        JPanel trailing = new JPanel();
        trailing.setOpaque(false);
        trailing.setLayout(new BoxLayout(trailing, BoxLayout.X_AXIS));
        trailing.setBorder(new EmptyBorder(0, 4, 0, 4));
        trailing.add(clear);
        trailing.add(Box.createHorizontalStrut(4));
        trailing.add(chevron);
        tf.setTrailingComponent(trailing);

        return tf;
    }

    private JComponent phoneInput() {
        try {
            // Masked field that accepts a US phone pattern.
            MaskFormatter mf = new MaskFormatter("(###) ###-####");
            mf.setPlaceholderCharacter('_');
            JFormattedTextField ftf = new JFormattedTextField(mf);
            ftf.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(___) ___-____");
            return ftf;
        } catch (ParseException e) {
            // Fallback plain text field if formatter creation fails.
            return new JTextField("(123) 456-7890");
        }
    }

    private JComponent passwordInput() {
        // Password input field.
        JPasswordField pf = new JPasswordField();
        pf.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        // Reveal button is controlled by theme property PasswordField.showRevealButton
        return pf;
    }

    private JComponent numInput() {
        // Numeric spinner with min/max/step constraints.
        SpinnerNumberModel model = new SpinnerNumberModel(1, 0, 999, 1);
        return new JSpinner(model);
    }

    private JComponent multilineInput() {
        // Multiline text input area.
        JTextArea ta = new JTextArea(4, 22);
        ta.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Placeholder");
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        // Scrollable wrapper for the text area.
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(320, 120));
        return sp;
    }

    private JComponent labeled(String label, JComponent c) {
        // Reusable "label + component" vertical block.
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // Caption above the component.
        JLabel l = new JLabel(label);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);

        p.add(Box.createVerticalStrut(6));

        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(c);

        return p;
    }
}
