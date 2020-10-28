package car.server;

public final class OperationParser {
	private static int PreviousResult;
	
	/**
	 * Parse et calcule le résultat de l'opération envoyée par le client
	 * @param msg le message reçu par le client
	 * @return le résultat de l'opération
	 */
	public static int Parse(String msg) {
		String[] list = msg.split(" ");
		if (list.length == 0) {
			throw new IllegalArgumentException("Aucune opération à traiter");
		}else if (list.length == 1) {
			PreviousResult = Integer.parseInt(msg);
		}else {
			int first;
			int second = Integer.parseInt(list[list.length-1]);
			String operand = list[list.length-2];
			if (list.length == 2) {
				first = PreviousResult;
			} else if (list.length == 3) {
				first = Integer.parseInt(list[0]);
			}else {
				throw new IllegalArgumentException("");
			}
			PreviousResult = Calculate(operand, first, second);
		}
		return PreviousResult;
    }
    
	/**
	 * Calcule le résultat de l'opération donnée
	 * @param operand le calcul à effectuer
	 * @param first la première partie du calcul
	 * @param second la deuxième partie du calcul
	 * @return le résultat de l'opération
	 */
    private static int Calculate(String operand, int first, int second) {
    	switch (operand) {
    		case "*":
    			return first * second;
    		case "/":
        		return first / second;
    		case "-":
        		return first - second;
    		case "+":
        		return first + second;
    	}
    	throw new IllegalArgumentException(operand + " n'est pas un opérateur valide");
    }
}