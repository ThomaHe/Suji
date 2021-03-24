package fr.henry.tapptic.data;

import fr.henry.tapptic.network.NumbersResponse;

public class NumbersMapper {

    public static Numbers MapResponseToNumbers(NumbersResponse nbrResponse){
        Numbers nbr= new Numbers();
        nbr.setName(nbrResponse.getName());
        nbr.setImage(httpToHttps(nbrResponse.getImage()));
        nbr.setText(nbrResponse.getText());
        return nbr;
    }

    private static String httpToHttps(String link) // il faut passer les adresses en https pour télécharger les images
    {
        StringBuilder str = new StringBuilder(link);
        str.insert(4,"s");
        return  str.toString();
    }
}
