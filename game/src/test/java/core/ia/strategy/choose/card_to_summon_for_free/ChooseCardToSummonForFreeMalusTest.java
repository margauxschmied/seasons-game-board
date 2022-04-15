package core.ia.strategy.choose.card_to_summon_for_free;

import core.board.FacadeIA;
import core.cards.Card;
import core.cards.effects.*;
import core.ia.Player;
import core.util.CardUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ChooseCardToSummonForFreeMalusTest {
    private ChooseCardToSummonForFreeMalus chooseCardToSummonForFreeMalus;
    private Player player;
    private FacadeIA facade;

    @BeforeEach
    void setup() {
        chooseCardToSummonForFreeMalus = new ChooseCardToSummonForFreeMalus();
        player = Mockito.mock(Player.class);
        facade = Mockito.mock(FacadeIA.class);
        when(player.getFacadeIA()).thenReturn(facade);
    }

    @Test
    void chooseWithMalusCard() {
        Card kairn = Mockito.mock(KairnDestructeurEffect.class);
        List<Card> cardsToChooseFrom = new ArrayList<>(Arrays.asList(
                Mockito.mock(CaliceDivinEffect.class),
                Mockito.mock(GrimoireEnsorceleEffect.class),
                Mockito.mock(MainFortuneEffect.class),
                Mockito.mock(BottesTemporelles.class),
                Mockito.mock(HeaumeRagfieldEffect.class),
                Mockito.mock(BalanceIshtarEffect.class),
                Mockito.mock(KairnDestructeurEffect.class),
                Mockito.mock(NariaLaProphetesseEffect.class),
                Mockito.mock(EarthAmuletEffect.class)));
        when(facade.getCardInHand()).thenReturn(new ArrayList<>(cardsToChooseFrom));
        assertEquals(kairn.getClass(), chooseCardToSummonForFreeMalus.choose(player).getClass());
    }

    @Test
    void chooseWithoutMalusCard() {
        List<Card> cardsToChooseFrom = new ArrayList<>(Arrays.asList(
                Mockito.mock(CaliceDivinEffect.class),
                Mockito.mock(GrimoireEnsorceleEffect.class),
                Mockito.mock(MainFortuneEffect.class),
                Mockito.mock(BottesTemporelles.class),
                Mockito.mock(HeaumeRagfieldEffect.class),
                Mockito.mock(BalanceIshtarEffect.class),
                Mockito.mock(MainFortuneEffect.class),
                Mockito.mock(NariaLaProphetesseEffect.class),
                Mockito.mock(EarthAmuletEffect.class)));
        when(facade.getCardInHand()).thenReturn(new ArrayList<>(cardsToChooseFrom));
        assertNull(chooseCardToSummonForFreeMalus.choose(player));
    }

    @Test
    void chooseWithEmptyHand() {
        when(facade.getCardInHand()).thenReturn(new ArrayList<>());
        assertNull(chooseCardToSummonForFreeMalus.choose(player));
    }
}