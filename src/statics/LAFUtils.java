package statics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

import gui.button.CustomButtonUI;
import gui.internalframe.CustomInternalFrameProperties;
import gui.menu.CustomMenuUI;
import gui.menuitem.CustomMenuItemUI;
import gui.scroll.ScrollBarColorDecorator;
import gui.tab.SimpleColorTabbedPaneUI;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Dec 15, 2015, 1:23:21 AM 
 */
public class LAFUtils {
	
	public static void setAllLAFFonts( Font f ) {
		Arrays.asList( new String[] { "ArrowButton.font", "Button.font", "CheckBox.font", "CheckBoxMenuItem.font", "ColorChooser.font", "ComboBox.font", "DesktopIcon.font", "DesktopPane.font",
				"EditorPane.font", "FileChooser.font", "FormattedTextField.font", "InternalFrame.font", "InternalFrame.titleFont", "InternalFrameTitlePane.font", "Label.font", "List.font",
				"Menu.font", "MenuBar.font", "MenuItem.font", "OptionPane.font", "Panel.font", "PasswordField.font", "PopupMenu.font", "PopupMenuSeparator.font", "ProgressBar.font", "RadioButton.font",
				"RootPane.font", "ScrollBar.font", "ScrollBarThumb.font", "ScrollBarTrack.font", "ScrollPane.font", "Separator.font", "Slider.font", "SliderThumb.font", "SliderTrack.font",
				"Spinner.font", "SplitPane.font", "TabbedPane.font", "Table.font", "TableHeader.font", "TextArea.font", "TextField.font", "TextPane.font", "TitledBorder.font", "ToggleButton.font",
				"ToolBar.font", "ToolTip.font", "Tree.font", "Viewport.font", "defaultFont" } ).forEach( s -> UIManager.put( s, f ) );
	}
	
	public static void setThemedScrollPane( JScrollPane scroll, Color bg, Color fg ) {
		ScrollBarColorDecorator.decorate( scroll, bg, fg );
	}
	
	public static void setThemedButton( JButton b ) {
		b.setUI( new CustomButtonUI() );
	}
	
	public static void setThemedTabPane( JTabbedPane t, Color bg, Color fg ) {
		SimpleColorTabbedPaneUI tabUI = new SimpleColorTabbedPaneUI( bg, fg );
		t.setOpaque( true );  //if this isn't set, tab backgrounds are not properly painted in certain look and feels like Nimbus
		Insets none = new Insets( 0, 0, 0, 0 );	
		tabUI.setContentBorderInsets( none );
		tabUI.setTabAreaInsets( none );
		t.setUI( tabUI );
	}
	
	public static void setAllLAFInternalFrames() {
		new CustomInternalFrameProperties();
	}
	
	public static void setAllLAFInternalFrames( Color fore, Color back ) {
		new CustomInternalFrameProperties( fore, back );
	}
	
	public static void setAllLAFInternalFrames( Color fore, Color back, int buttonWidth, int buttonHeight, int titleHeight ) {
		new CustomInternalFrameProperties( fore, back, buttonWidth, buttonHeight, titleHeight );
	}
	
	public static void applyThemedUI( JMenuBar bar, Color bg, Color fg ) {
		applySimpleUI( bar, bg, fg );
	}
	
	public static void applySimpleUI( JComponent comp, Color bg, Color fg ) {
		for ( Component c : comp.getComponents() ) {
			if ( c instanceof JMenuItem ) {
				if ( !( c instanceof JMenu ) ) {
					( (JMenuItem)c ).setUI( new CustomMenuItemUI() );
				} else {
					( (JMenu)c ).setUI( new CustomMenuUI() );
					JPopupMenu pop = ( (JMenu)c ).getPopupMenu();
					pop.setUI( new BasicPopupMenuUI() );
					pop.setBackground( bg );
					pop.setForeground( fg );
					pop.setBorder( BorderFactory.createLineBorder( fg.darker() ) );
					applySimpleUI( pop, bg, fg );
				}
			} else if ( c instanceof JSeparator ) {
				JSeparator sep = ( (JSeparator)c );
				sep.setBackground( fg );
				sep.setOpaque( true );
			} else if ( c instanceof JComboBox ) {
				JComboBox<?> combo = (JComboBox<?>)c;
				combo.setUI( new BasicComboBoxUI() );
			}
		}
	}
}