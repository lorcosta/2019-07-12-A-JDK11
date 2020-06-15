package it.polito.tdp.food.db;

import java.util.List;

import it.polito.tdp.food.model.Food;

public class TestDao {

	public static void main(String[] args) {
		FoodDao dao = new FoodDao();
		
		System.out.println("Printing all the condiments...");
		//System.out.println(dao.listAllCondiments());
		
		System.out.println("Printing all the foods...");
		//System.out.println(dao.listAllFoods());
		
		System.out.println("Printing all the portions...");
		//System.out.println(dao.listAllPortions());
		
		List<Food> cibi=dao.listFoodVertices(2);
		System.out.println(cibi);
	}

}
