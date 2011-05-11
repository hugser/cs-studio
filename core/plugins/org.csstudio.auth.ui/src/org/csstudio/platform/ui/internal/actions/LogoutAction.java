package org.csstudio.platform.ui.internal.actions;

import org.csstudio.auth.internal.usermanagement.IUserManagementListener;
import org.csstudio.auth.internal.usermanagement.UserManagementEvent;
import org.csstudio.platform.security.Credentials;
import org.csstudio.platform.security.ILoginCallbackHandler;
import org.csstudio.platform.security.SecurityFacade;
import org.csstudio.platform.ui.CSSPlatformUiPlugin;
import org.csstudio.platform.ui.security.UiLoginCallbackHandler;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;


/**
 * Logged in user can logout from this action. Its same as switch to anonymous user.
 * @author Xihui Chen
 *
 */
public class LogoutAction extends Action implements IWorkbenchAction, IUserManagementListener{

	
	@SuppressWarnings("unused")
	private final IWorkbenchWindow window;
	public final static String ID = "org.csstudio.platform.ui.internal.actions.LogoutAction";
	
	public LogoutAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText("Logout");
		setToolTipText("Logout");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				CSSPlatformUiPlugin.getDefault().getPluginId(), "icons/logout.png"));
		SecurityFacade.getInstance().addUserManagementListener(this);
		setEnabled(SecurityFacade.getInstance().getCurrentUser() != null);
	}
	
	@Override
	public void run() {
		SecurityFacade sf = SecurityFacade.getInstance();
		ILoginCallbackHandler oldLCH = sf.getRegisteredLoginCallbackHandler();
		//Logout is same as login as anonymous.
		sf.setLoginCallbackHandler(new UiLoginCallbackHandler("","", Credentials.ANONYMOUS));
		sf.authenticateApplicationUser();
		sf.setLoginCallbackHandler(oldLCH);
	}

	public void dispose() {
		SecurityFacade.getInstance().removeUserManagementListener(this);
	}

	public void handleUserManagementEvent(UserManagementEvent event) {
		setEnabled(SecurityFacade.getInstance().getCurrentUser() != null);
	}
}
