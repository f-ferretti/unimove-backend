CREATE TABLE users (
                       id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       username            VARCHAR(100) NOT NULL UNIQUE,
                       email               VARCHAR(255) NOT NULL UNIQUE,
                       full_name           VARCHAR(255) NOT NULL,
                       role                VARCHAR(20)  NOT NULL DEFAULT 'STUDENT',
                       avatar_url          VARCHAR(500),
                       iban                VARCHAR(50),
                       iban_holder         VARCHAR(255),
                       travel_preferences  VARCHAR(500),
                       created_at          TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE route_preferences (
                                   id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                   user_id     UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                   city_from   VARCHAR(100) NOT NULL,
                                   city_to     VARCHAR(100) NOT NULL,
                                   created_at  TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE rides (
                       id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       driver_id           UUID NOT NULL REFERENCES users(id),
                       departure_city      VARCHAR(100) NOT NULL,
                       departure_time      TIMESTAMP NOT NULL,
                       arrival_city        VARCHAR(100) NOT NULL,
                       arrival_time_est    TIMESTAMP,
                       hotspot_1           VARCHAR(100),
                       hotspot_2           VARCHAR(100),
                       hotspot_3           VARCHAR(100),
                       vehicle_model       VARCHAR(100),
                       vehicle_plate       VARCHAR(20),
                       total_seats         INT NOT NULL DEFAULT 4,
                       available_seats     INT NOT NULL DEFAULT 4,
                       travel_preferences  VARCHAR(500),
                       status              VARCHAR(20) NOT NULL DEFAULT 'OPEN',
                       created_at          TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE bookings (
                          id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          ride_id         UUID NOT NULL REFERENCES rides(id) ON DELETE CASCADE,
                          passenger_id    UUID NOT NULL REFERENCES users(id),
                          hotspot_chosen  VARCHAR(100),
                          status          VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
                          created_at      TIMESTAMP NOT NULL DEFAULT now(),
                          UNIQUE(ride_id, passenger_id)
);

CREATE TABLE reviews (
                         id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         ride_id     UUID NOT NULL REFERENCES rides(id),
                         reviewer_id UUID NOT NULL REFERENCES users(id),
                         reviewed_id UUID NOT NULL REFERENCES users(id),
                         rating      INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                         created_at  TIMESTAMP NOT NULL DEFAULT now(),
                         UNIQUE(ride_id, reviewer_id)
);

CREATE TABLE notifications (
                               id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               user_id     UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               type        VARCHAR(50) NOT NULL,
                               message     VARCHAR(500),
                               is_read     BOOLEAN NOT NULL DEFAULT false,
                               ride_id     UUID REFERENCES rides(id),
                               created_at  TIMESTAMP NOT NULL DEFAULT now()
);