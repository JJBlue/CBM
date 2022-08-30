package cbm.modules.sudo.sudoplayer;

public interface SudoPlayerInterface {
	boolean isEditablePermissions(boolean permissions);
	SudoPlayerInterface setEditablePermissions(boolean permissions);
	
	boolean hasAllPermissions();
	SudoPlayerInterface setAllPermissions(boolean permissions);
	
	boolean isSilentOutputMessage();
	SudoPlayerInterface setSilentOutputMessage(boolean silent);
}
