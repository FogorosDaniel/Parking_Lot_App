<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>
    <div class="container text-center">
        <div class="row">
            <div class="col">
                car1
            </div>
            <div class="col">
                spot1
            </div>
            <div class="col">
                owner1
            </div>
        </div>
            <div class="row">
                <div class="col">
                    car2
                </div>
                <div class="col">
                    spot2
                </div>
                <div class="col">
                    owner2
                </div>
            </div>
        <div class="row">
            <div class="col">
                car3
            </div>
            <div class="col">
                spot3
            </div>
            <div class="col">
                owner3
            </div>
        </div>

        </div>
    </div>
    <h5>Free parking spots: ${numberOfFreeParkingSpots}</h5>
</t:pageTemplate>
