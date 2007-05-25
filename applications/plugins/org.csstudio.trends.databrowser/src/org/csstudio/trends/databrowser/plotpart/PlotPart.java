package org.csstudio.trends.databrowser.plotpart;

import java.io.InputStream;

import org.csstudio.swt.chart.Chart;
import org.csstudio.trends.databrowser.model.Model;
import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;

/** Base for a ViewPart or EditorPart that displays a DataBrowser plot.
 *  @author Kay Kasemir
 */
public class PlotPart
{
    private Model model = new Model();
    private Controller controller;
    private BrowserUI gui;
    private IFile file;

    /** Initialize the model from given file. */
    public void init(IFile file) throws PartInitException
    {
        if (file == null)
            return;
        // Load model content from file
        try
        {
            InputStream stream = file.getContents();
            model.load(stream);
            stream.close();
        }
        catch (Exception ex)
        {
            throw new PartInitException("Load error for "  //$NON-NLS-1$
                            + file.getName(), ex);
        }
        this.file = file;
    }
    
    /** Get a name suitable for the part's title.
     *  <p>
     *  That's the plain file name, without path and suffix,
     *  or "" which translates into the default title.
     */
    @SuppressWarnings("nls")
    public String getPartName()
    {
        if (file == null)
            return "";
        final String name = file.getName();
        final int dot = name.lastIndexOf(".");
        return dot > 1  ?  name.substring(0, dot)  :  name;
    }
    
    /** @return The model. */
    public Model getModel()
    {  
        return model;
    }
    
    /** @return The Chart within the GUI. */
    public Chart getChart()
    {
        return gui.getChart();
    }
    
    /** Creates the SWT controls for DataBrowser plot.
     *  @see IWorkbenchPart#createPartControl
     */
    public void createPartControl(Composite parent)
    {
        gui = new BrowserUI(model, parent, 0);
        controller = new Controller(model, gui);
    }

    /** @see IWorkbenchPart#setFocus */
    public void setFocus()
    {   
        gui.setFocus(); 
    }
    
    /** Must be called for cleanup. */
    public void dispose()
    {
        gui.dispose();
        controller.dispose();
    }
}
