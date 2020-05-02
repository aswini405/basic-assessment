/**
 * 
 */
package com.ps.coding.basicassessment;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @author aswini.nellimarla
 *
 */
public class Cache {

	private int initCapacity;
	private Map<Integer, Object> entries;
	private Map<Integer, Integer> keyCouner;
	private Map<Integer, LinkedHashSet<Integer>> itemList;

	private int leastVal = -1;

	/**
	 * Cache constructor
	 * 
	 * @param capacity
	 */
	public Cache(int capacity) {
		this.initCapacity = capacity;
		this.entries = new HashMap<Integer, Object>();
		this.keyCouner = new HashMap<Integer, Integer>();
		this.itemList = new HashMap<Integer, LinkedHashSet<Integer>>();
		itemList.put(Integer.valueOf(1), new LinkedHashSet<Integer>());
	}

	/**
	 * Method put to store key and value and compute eviction key
	 *  
	 * @param key
	 * @param value
	 */
	public void put(int key, Object value) {
		if (initCapacity <= 0) {
			return;
		}
		if (entries.containsKey(key)) {
			entries.put(key, value);
			get(key);
			return;
		}
		evictKeyComputation();
		entries.put(key, value);
		keyCouner.put(key, 1);
		leastVal = 1;
		itemList.get(1).add(key);
	}

	/**
	 * Finds the least used key when entry size reaches max capacity
	 *   
	 */
	private void evictKeyComputation() {
		if (entries.size() >= initCapacity) {
			int keyToEvict = itemList.get(leastVal).iterator().next();
			itemList.get(leastVal).remove(keyToEvict);
			entries.remove(keyToEvict);
			keyCouner.remove(keyToEvict);
		}
	}

	/**
	 * Method get to retrieve value for supplied key
	 * 
	 * @param key
	 * @return
	 */
	public Object get(int key) {
		if (!entries.containsKey(key)) {
			return null;
		}
		int count = keyCouner.get(key);
		keyCouner.put(key, count + 1);
		itemList.get(count).remove(key);
		if (count == leastVal && itemList.get(count).size() == 0) {
			leastVal++;
		}
		if (!itemList.containsKey(count + 1)) {
			itemList.put(count + 1, new LinkedHashSet<Integer>());
		}
		itemList.get(count + 1).add(key);
		return entries.get(key);
	}

	/**
	 * Main method to execute test cases
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Cache cache = new Cache(2);
		cache.put(1, 1);
		cache.put(2, "Hello");
		System.out.println(cache.get(1));
		cache.put(3, 2.34);
		System.out.println(cache.get(2));
		System.out.println(cache.get(3));
		cache.put(4, "Test");
		System.out.println(cache.get(1));
		System.out.println(cache.get(3));
		System.out.println(cache.get(4));
	}

}
