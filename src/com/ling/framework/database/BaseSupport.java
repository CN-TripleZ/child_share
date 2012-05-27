package com.ling.framework.database;


public abstract class BaseSupport<T> {
	private IDBRouter baseDBRouter;
	protected IDAOSupport<T> baseDaoSupport;
	protected IDAOSupport<T> daoSupport;

	protected String getTableName(String moude) {
		return baseDBRouter.getTableName(moude);
	}

	public IDAOSupport<T> getBaseDAOSupport() {
		return baseDaoSupport;
	}

	public void setBaseDAOSupport(IDAOSupport<T> baseDaoSupport) {
		this.baseDaoSupport = baseDaoSupport;
	}

	public void setDAOSupport(IDAOSupport<T> daoSupport) {
		this.daoSupport = daoSupport;
	}

	public IDBRouter getBaseDBRouter() {
		return baseDBRouter;
	}

	public void setBaseDBRouter(IDBRouter baseDBRouter) {
		this.baseDBRouter = baseDBRouter;
	}
}
