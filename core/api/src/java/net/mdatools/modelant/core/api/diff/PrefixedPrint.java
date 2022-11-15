package net.mdatools.modelant.core.api.diff;

/**
 * Allow printing as a nested object 
 * @author popovr
 */
public interface PrefixedPrint {

  /**
   * @param prefix not null
   * @return toString representation with the prefix added on every new line (but the first)
   */
  String toString(String prefix);
}
