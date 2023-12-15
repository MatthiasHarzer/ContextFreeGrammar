package symbols;

import java.util.List;

public record Concat(Symbol... symbols) {

    public static Concat empty(){
        return new Concat();
    }



    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj.getClass() == this.getClass())) return false;
        Concat s = (Concat) obj;
        if(s.symbols.length != symbols.length) return false;
        for(int i = 0; i < symbols.length; i++){
            if(!s.symbols[i].equals(symbols[i])) return false;
        }
        return true;
    }

    @Override
    public Concat clone(){
        return new Concat(List.of(symbols).toArray(Symbol[]::new));
    }
}
