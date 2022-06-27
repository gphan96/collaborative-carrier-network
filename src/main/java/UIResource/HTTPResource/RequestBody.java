package UIResource.HTTPResource;

import org.json.JSONArray;
import org.json.JSONObject;

import Agent.Agent;
import Agent.CarrierAgent;
import Auction.Auction;
import Auction.Bid;
import Auction.TransportRequest;

public class RequestBody {

    // Agent

    public static String login(String username, String password) {
        JSONObject data = new JSONObject();
        data.put("Username", username);
        data.put("Password", password);
        return body("login", null, data);
    }

    public static String registerAuctioneer(String name, String username, String password) {
        JSONObject data = new JSONObject();
        data.put("Username", username);
        data.put("Name", name);
        data.put("Password", password);
        data.put("IsAuctioneer", true);
        return body("register", null, data);
    }

    public static String registerCarrier(String name, String username, String password, float depotX, float depotY,
            float pickupBaserate, float externalTravelCost, float loadBaserate, float internalTravelCost) {
        JSONObject data = new JSONObject();
        data.put("Username", username);
        data.put("Name", name);
        data.put("Password", password);
        data.put("IsAuctioneer", false);
        data.put("Vehicle", username);
        data.put("DepotLat", depotX);
        data.put("DepotLon", depotY);
        data.put("PickupBaserate", pickupBaserate);
        data.put("TravelCostPerKM", externalTravelCost);
        data.put("LoadBaserate", loadBaserate);
        data.put("InternalTravelCostPerKM", internalTravelCost);
        return body("register", null, data);
    }

    public static String getAgent(String username, String token) {
        JSONObject data = new JSONObject();
        data.put("Username", username);
        return body("getAgent", token, data);
    }

    public static String getAllAgents(String token) {
        return body("getAgents", token, null);
    }

    public static String getCarrierAgents(String token) {
        return body("getCarriers", token, null);
    }

    public static String getAuctioneerAgents(String token) {
        return body("getAuctioneers", token, null);
    }

    // Transport request

    public static String addTransportRequest(CarrierAgent agent, float pickX, float pickY, float delX, float delY, String token) {
        JSONArray data = new JSONArray();
        JSONObject objInArray = new JSONObject();
        objInArray.put("Agent", agent.getUsername());
        objInArray.put("PickupLat", pickX);
        objInArray.put("PickupLon", pickY);
        objInArray.put("DeliveryLat", delX);
        objInArray.put("DeliveryLon", delY);
        data.put(objInArray);
        return body("addRequest", token, data);
    }

    public static String getAllTransportRequests(String token) {
        return body("getRequests", token, null);
    }

    public static String getTransportRequestsOfAgent(Agent agent, String token) {
        JSONObject data = new JSONObject();
        data.put("Agent", agent.getUsername());
        return body("getRequestsOfAgent", token, data);
    }

    public static String getTransportRequestsOfAuction(Auction auction, String token) {
        JSONObject data = new JSONObject();
        data.put("Auction", auction.getID());
        return body("getRequestsOfAuction", token, data);
    }

    // Auction

    public static String addAuction(String token) {
        return body("addAuction", token, null);
    }

    public static String getAuctions(String token) {
        return body("getAuctions", token, null);
    }

    public static String addRequestToAuction(Auction auction, TransportRequest request, String token) {
        JSONObject data = new JSONObject();
        data.put("Auction", auction.getID());
        data.put("TransportRequest", request.getID());
        return body("addRequestToAuction", token, data);
    }

    public static String getBids(Auction auction, String token) {
        JSONObject data = new JSONObject();
        data.put("Auction", auction.getID());
        return body("getBids", token, data);
    }

    public static String startAuction(Auction auction, String token) {
        JSONObject data = new JSONObject();
        data.put("Auction", auction.getID());
        return body("startAuction", token, data);
    }

    public static String endAuction(Auction auction, String token) {
        JSONObject data = new JSONObject();
        data.put("Auction", auction.getID());
        return body("endAuction", token, data);
    }

    public static String setWinner(Auction auction, Bid bid, String token) {
        JSONObject data = new JSONObject();
        data.put("Auction", auction.getID());
        data.put("Username", bid == null ? null : bid.getBidder().getUsername());
        data.put("Cost", bid == null ? null : bid.getPayPrice());
        return body("setWinner", token, data);
    }

    public static String addMinProfitToBid(CarrierAgent carrier, double price, String token) {
        JSONObject data = new JSONObject();
        data.put("Username", carrier.getUsername());
        data.put("Price", price);
        return body("addMinProfitToBid", token, data);
    }

    public static String getMinProfitToBid(CarrierAgent carrier, String token) {
        JSONObject data = new JSONObject();
        data.put("Username", carrier.getUsername());
        return body("getMinProfitToBid", token, data);
    }

    public static String addMaxProfitToAuctionOff(CarrierAgent carrier, double price, String token) {
        JSONObject data = new JSONObject();
        data.put("Username", carrier.getUsername());
        data.put("Price", price);
        return body("addMaxProfitToAuctionOff", token, data);
    }

    public static String getMaxProfitToAuctionOff(CarrierAgent carrier, String token) {
        JSONObject data = new JSONObject();
        data.put("Username", carrier.getUsername());
        return body("getMinProfitToBid", token, data);
    }

    // Helper

    private static String body(String command, String token, Object data) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("Cmd", command);
        if (token != null) {
            requestBody.put("Token", token);
        }
        if (data != null) {
            requestBody.put("Data", data);
        }
        return requestBody.toString();
    }

}
