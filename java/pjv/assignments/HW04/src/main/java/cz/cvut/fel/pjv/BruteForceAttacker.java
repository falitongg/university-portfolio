package cz.cvut.fel.pjv;

public class BruteForceAttacker extends Thief {
    int pokus = 0;
    @Override

    public void breakPassword(int sizeOfPassword){
        char[] characters = getCharacters();
        char[] password = new char[sizeOfPassword];
        pokus = 0;
        tryPass(characters, password, 0);
    }
    public void tryPass(char[] chars, char[] password, int pos){
        if(isOpened()){
            return;
        }

        if(pos == password.length){
            System.out.println(password);
            pokus++;
            tryOpen(password);
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            password[pos] = chars[i];
            tryPass(chars, password, pos + 1);
            if (isOpened()){
                return;
            }
        }
    }
    public int getPokus(){
        return pokus;
    }
}
