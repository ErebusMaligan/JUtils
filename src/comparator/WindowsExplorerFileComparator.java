package comparator;

import java.io.File;
import java.util.Comparator;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Apr 7, 2016, 2:33:39 AM 
 */
public class WindowsExplorerFileComparator implements Comparator<File> {

	private final Comparator<String> NATURAL_SORT = new WindowsExplorerStringComparator();
	
    @Override
    public int compare(File o1, File o2) {;
        return NATURAL_SORT.compare(o1.getName(), o2.getName());
    }

}