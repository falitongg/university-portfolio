package cz.cvut.fel.omo.cv8.expressions;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.Map;

public class Context {
    
    // MY IMPLEMENTATION

    private final Map<String, ImmutableList<Integer>> vars = new HashMap<>();

    public void put(String name, ImmutableList<Integer> value) {
        vars.put(name, value);
    }
    public ImmutableList<Integer> get(String name) {
        return vars.get(name);
    }
    
}
