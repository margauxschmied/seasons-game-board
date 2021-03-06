package core.ia.strategy.choose.energy_to_get;

import core.board.Board;
import core.board.FacadeIA;
import core.board.enums.Energy;
import core.cards.Card;
import core.game.App;
import core.game.GameController;
import core.ia.Player;
import core.ia.PlayerFactory;
import core.ia.TypeAIPlayer;
import core.util.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ChooseEnergyToGetPrefInvocTest {
    private Config config = new Config();
    private GameController controller = new GameController();
    private Board board;
    private FacadeIA facadeIA;
    private ChooseEnergyToGetPrefInvoc chooseEnergyToGetPrefInvoc;
    private Card card1, card2;
    private Player player;
    private Player[] players;


    @BeforeEach
    void setUp() {
        players = App.initPlayer(1, config);
        PlayerFactory playerFactory=new PlayerFactory(config);
        players[0]=playerFactory.getPlayer(TypeAIPlayer.RANDOM, "IA");
        controller.initGame(players, config);
        board = controller.getBoard();
        player = players[0];

        facadeIA = new FacadeIA(board, player, config );
        player.setFacadeIA(facadeIA);

        chooseEnergyToGetPrefInvoc = new ChooseEnergyToGetPrefInvoc();
        card1 = Mockito.mock(Card.class);
        card2 = Mockito.mock(Card.class);

        board.getInventories().get(players[0]).getInvocation().setInvocationPoints(1);

        board.initHand(players[0]);

        board.getInventories().get(players[0]).addCrystals(100);

    }

    @Test
    void chooseEnergyFire() {
        when(card1.getCrystalCost()).thenReturn(0);
        when(card2.getCrystalCost()).thenReturn(0);
        when(card1.getEnergyCost()).thenReturn(List.of(Energy.FIRE));
        when(card2.getEnergyCost()).thenReturn(List.of());

        board.getInventories().get(players[0]).getHand().setCardsInHand(new ArrayList<>(List.of(card1, card2)));

        assertEquals(Energy.FIRE, chooseEnergyToGetPrefInvoc.choose(player));
    }

    @Test
    void chooseEnergyWind() {
        when(card1.getCrystalCost()).thenReturn(0);
        when(card2.getCrystalCost()).thenReturn(0);
        when(card1.getEnergyCost()).thenReturn(List.of(Energy.FIRE));
        when(card2.getEnergyCost()).thenReturn(List.of(Energy.WIND));

        board.getInventories().get(players[0]).getEnergyStock().addEnergy(Energy.FIRE);

        board.getInventories().get(players[0]).getHand().setCardsInHand(new ArrayList<>(List.of(card1, card2)));

        assertEquals(Energy.WIND, chooseEnergyToGetPrefInvoc.choose(player));
    }

    @Test
    void chooseFireAndWind() {
        when(card1.getCrystalCost()).thenReturn(0);
        when(card2.getCrystalCost()).thenReturn(0);
        when(card1.getEnergyCost()).thenReturn(List.of(Energy.FIRE));
        when(card2.getEnergyCost()).thenReturn(List.of(Energy.WIND));

        board.getInventories().get(players[0]).getHand().setCardsInHand(new ArrayList<>(List.of(card1, card2)));

        assertEquals(Energy.FIRE, chooseEnergyToGetPrefInvoc.choose(player));
    }

}