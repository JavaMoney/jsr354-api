/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package net.java.javamoney.ri.common;

/**
 * Common base class that allows to implement immutable instances that can be
 * set to immutable after configuring. Immutability can be implemented by
 * explicitly call {@link #ensureWritable()} within all mutable methods
 * implemented by the superclass.
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractConfigurableItem {
	/** Current read-only state. */
	private boolean readOnly;

	/**
	 * Allows to check if the instance is read-only.
	 * 
	 * @return true, if read-only.
	 */
	public final boolean isReadOnloy() {
		return this.readOnly;
	}

	/**
	 * This method allows to set an instance to be read-only. Instances that are
	 * read-only can never be set to mutable again.
	 * 
	 * @see #isReadOnloy()
	 */
	public final void setReadOnly() {
		this.readOnly = true;
	}

	/**
	 * Method to be called by methods implemented in the superclasses, to allow
	 * changing of instances only, if they are not read-only.
	 * 
	 * @see #isReadOnloy()
	 * @see #setReadOnly()
	 * @throws IllegalStateException
	 *             if the instance is already set to read-only.
	 */
	protected final void ensureWritable() {
		if (readOnly) {
			throw new IllegalStateException(
					"Item is readOnly and can not be changed.");
		}
	}

}
